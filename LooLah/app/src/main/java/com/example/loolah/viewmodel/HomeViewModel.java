package com.example.loolah.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Toilet;
import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;
import com.example.loolah.util.LiveDataWrapper;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private final CollectionReference tColRef;
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> toiletListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> filteredToiletListMutableLiveData = new MutableLiveData<>();

    public HomeViewModel() {
        tColRef = FirebaseFirestore.getInstance().collection("toilets");
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getFilteredToiletList() {
        if (filteredToiletListMutableLiveData == null)
            filteredToiletListMutableLiveData = new MutableLiveData<>();
        return filteredToiletListMutableLiveData;
    }

    public void getToilets(Location location) {
        filteredToiletListMutableLiveData.setValue(LiveDataWrapper.loading(null));

        final GeoLocation currentLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
        final double distanceRadius = 5 * 1000; // Distance Radius = 5km
        final ArrayList<Task<QuerySnapshot>> tasks = new ArrayList<>();

        for (GeoQueryBounds b : GeoFireUtils.getGeoHashQueryBounds(currentLocation, distanceRadius))
            tasks.add(tColRef.orderBy("geoHash").startAt(b.startHash).endAt(b.endHash).get());

        Tasks.whenAllComplete(tasks).addOnSuccessListener(completedTasks -> {
            ArrayList<Toilet> toiletList = new ArrayList<>();

            for (Task<QuerySnapshot> task : tasks) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    double toiletDistance = GeoFireUtils.getDistanceBetween((new GeoLocation(documentSnapshot.getDouble("latitude"), documentSnapshot.getDouble("longitude"))), currentLocation);
                    if (toiletDistance <= distanceRadius) {
                        Toilet toilet = documentSnapshot.toObject(Toilet.class);
                        toilet.setDistance(toiletDistance);
                        toiletList.add(toilet);
                    }
                }
            }

            toiletList.sort((toilet1, toilet2) -> (int) (toilet1.getDistance() - toilet2.getDistance()));
            toiletListMutableLiveData.setValue(LiveDataWrapper.success(toiletList));
            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(toiletList));
        }).addOnFailureListener(e -> filteredToiletListMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null)));
    }

    public void filterToilets(String keyword, ToiletType type, ToiletDistrict district, double distance, int rating) {
        try {
            ArrayList<Toilet> toiletList = toiletListMutableLiveData.getValue().getData();
            ArrayList<Toilet> filteredToiletList = new ArrayList<>();

            for (Toilet toilet : toiletList) {
                if (toilet.getName().toLowerCase().contains(keyword.toLowerCase()) && toilet.getDistance() <= distance && toilet.getRating() >= rating && (type == null || toilet.getType() == type) && (district == null || toilet.getDistrict() == district)) {
                    filteredToiletList.add(toilet);
                }
            }

            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(filteredToiletList));
        } catch (Exception e) {
            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.error("Something went wrong when getting toilets", null));
        }
    }
}
