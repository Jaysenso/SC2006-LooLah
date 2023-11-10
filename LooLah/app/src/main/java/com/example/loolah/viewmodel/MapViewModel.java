package com.example.loolah.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Toilet;
import com.example.loolah.util.LiveDataWrapper;
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

    public MapViewModel() {
        tColRef = Objects.requireNonNull(FirebaseFirestore.getInstance().collection("toilets"));
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getToiletList() {
        return toiletListMutableLiveData;
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
}