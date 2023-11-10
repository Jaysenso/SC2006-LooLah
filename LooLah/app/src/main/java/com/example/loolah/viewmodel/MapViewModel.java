package com.example.loolah.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Toilet;
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
import java.util.Objects;

public class MapViewModel extends ViewModel {
    private final CollectionReference tColRef;
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> toiletListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> filteredToiletListMutableLiveData = new MutableLiveData<>();

    public MapViewModel() {
        tColRef = Objects.requireNonNull(FirebaseFirestore.getInstance().collection("toilets"));
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getToiletList() {
        if (toiletListMutableLiveData == null) toiletListMutableLiveData = new MutableLiveData<>();
        return toiletListMutableLiveData;
    }
    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getFilteredToiletList() {
        if (filteredToiletListMutableLiveData == null) filteredToiletListMutableLiveData = new MutableLiveData<>();
        return filteredToiletListMutableLiveData;
    }

    public void getToilets() {
        toiletListMutableLiveData.setValue(LiveDataWrapper.loading(null));
        final ArrayList<Task<QuerySnapshot>> tasks = new ArrayList<>();

        tasks.add(tColRef.orderBy("geoHash").get());

        Tasks.whenAllComplete(tasks).addOnSuccessListener(completedTasks -> {
            ArrayList<Toilet> toiletList = new ArrayList<>();

            for (Task<QuerySnapshot> task : tasks) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Toilet toilet = documentSnapshot.toObject(Toilet.class);
                        toiletList.add(toilet);
                        Log.d("SUCCESSFUL QUERY",toilet.getName());
                    }
                }
                else {
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                }
            }
            toiletListMutableLiveData.setValue(LiveDataWrapper.success(toiletList));
        });
    }

    public void getFilteredToilets(Location location) {
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
                        Log.d("IN MAPVIEW:", toilet.getName());
                        toiletList.add(toilet);
                    }
                }
            }
            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(toiletList));
        });
    }
}