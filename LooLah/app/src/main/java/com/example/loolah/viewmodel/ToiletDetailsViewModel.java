package com.example.loolah.viewmodel;


import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loolah.R;
import com.example.loolah.model.ReviewDetails;
import com.example.loolah.model.ToiletDetails;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ToiletDetailsViewModel extends ViewModel {
    private final FirebaseUser user;
    private final CollectionReference uColRef;
    private final CollectionReference tColRef;
    private final CollectionReference rColRef;
    private MutableLiveData<LiveDataWrapper<ToiletDetails>> toiletMutableLiveData;
    private MutableLiveData<LiveDataWrapper<ArrayList<ReviewDetails>>> reviewListMutableLiveData;

    public ToiletDetailsViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        uColRef = FirebaseFirestore.getInstance().collection("users");
        tColRef = FirebaseFirestore.getInstance().collection("toilets");
        rColRef = FirebaseFirestore.getInstance().collection("reviews");
    }

    public MutableLiveData<LiveDataWrapper<ToiletDetails>> getToilet() {
        if (toiletMutableLiveData == null) toiletMutableLiveData = new MutableLiveData<>();
        return toiletMutableLiveData;
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<ReviewDetails>>> getReviewList() {
        if (reviewListMutableLiveData == null) reviewListMutableLiveData = new MutableLiveData<>();
        return reviewListMutableLiveData;
    }

    public void getToiletData(Context context, Resources resources, String toiletId) {
        toiletMutableLiveData.setValue(LiveDataWrapper.loading(null));
        tColRef.whereEqualTo("toiletId", toiletId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ToiletDetails toilet = queryDocumentSnapshots.getDocuments().get(0).toObject(ToiletDetails.class);

            if (toilet != null) {
                rColRef.whereEqualTo("toiletId", toilet.getToiletId()).whereEqualTo("creatorId", user.getUid()).count().get(AggregateSource.SERVER).addOnSuccessListener(aggregateQuerySnapshot -> {
                    if (aggregateQuerySnapshot.getCount() == 1) toilet.setReviewed(true);

                    getClosestTrainStation(context, toilet.getLongitude(), toilet.getLatitude(), trainStationName -> {
                        if (trainStationName != null) {
                            getClosestTrainStationCrowd(context, resources, trainStationName, trainStationCrowd -> getClosestCarParkCrowd(context, toilet.getLongitude(), toilet.getLatitude(), carParkCrowd -> {
                                if (carParkCrowd == null && trainStationCrowd == null)
                                    toilet.setCrowdLevel(5);
                                else {
                                    int carParkCrowdLevel = carParkCrowd == null ? 5 : Integer.parseInt(carParkCrowd) < 50 ? 4 : Integer.parseInt(carParkCrowd) < 100 ? 3 : Integer.parseInt(carParkCrowd) < 150 ? 2 : 1;
                                    int trainStationCrowdLevel = trainStationCrowd == null ? 5 : trainStationCrowd.equals("h") ? 4 : trainStationCrowd.equals("m") ? 2 : trainStationCrowd.equals("l") ? 1 : 5;
                                    toilet.setCrowdLevel(carParkCrowdLevel == 5 && trainStationCrowdLevel == 5 ? 5 : carParkCrowdLevel == 5 ? trainStationCrowdLevel : trainStationCrowdLevel == 5 ? carParkCrowdLevel : (carParkCrowdLevel + trainStationCrowdLevel) / 2);

                                    toiletMutableLiveData.setValue(LiveDataWrapper.success(toilet));
                                }
                            }));
                        } else {
                            getClosestCarParkCrowd(context, toilet.getLongitude(), toilet.getLatitude(), carParkCrowd -> {
                                toilet.setCrowdLevel(carParkCrowd == null ? 5 : Integer.parseInt(carParkCrowd) < 50 ? 4 : Integer.parseInt(carParkCrowd) < 100 ? 3 : Integer.parseInt(carParkCrowd) < 150 ? 2 : 1);
                                toiletMutableLiveData.setValue(LiveDataWrapper.success(toilet));
                            });
                        }
                    });
                });
            } else
                toiletMutableLiveData.setValue(LiveDataWrapper.error("Toilet does not exist", null));
        });
    }

    public void getToiletReviews(String toiletId) {
        ArrayList<ReviewDetails> reviewList = new ArrayList<>();

        reviewListMutableLiveData.setValue(LiveDataWrapper.loading(null));
        rColRef.whereEqualTo("toiletId", toiletId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                ReviewDetails review = document.toObject(ReviewDetails.class);

                if (review != null) {
                    uColRef.whereEqualTo("userId", review.getCreatorId()).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                        DocumentSnapshot userDocument = queryDocumentSnapshots1.getDocuments().get(0);
                        review.setCreatorUsername(userDocument.getString("username"));
                        review.setCreatorProfilePicUrl(userDocument.getString("profilePicUrl"));
                        if (review.getLikedBy().contains(user.getUid())) review.setLiked(true);
                        reviewList.add(review);

                        reviewListMutableLiveData.setValue(LiveDataWrapper.success(reviewList));
                    });
                }
            }

            reviewListMutableLiveData.setValue(LiveDataWrapper.success(reviewList));
        });
    }

    public void likeReview(String reviewId) {
        rColRef.document(reviewId).update("likedBy", FieldValue.arrayUnion(user.getUid()));
    }

    public void unlikeReview(String reviewId) {
        rColRef.document(reviewId).update("likedBy", FieldValue.arrayRemove(user.getUid()));
    }

    public interface VolleyCallback {
        void onSuccess(String result);
    }

    public void getClosestTrainStation(Context context, double longitude, double latitude, final VolleyCallback callback) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        String url = "https://places.googleapis.com/v1/places:searchNearby";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> {
                    try {
                        if (response.isNull("places")) callback.onSuccess(null);
                        else
                            callback.onSuccess(response.getJSONArray("places").getJSONObject(0).getJSONObject("displayName").getString("text"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> Log.d("TEST", "Error: " + error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("X-Goog-Api-Key", "AIzaSyBqHrxrSeyt_faB8zpgvzBfYSdtxS3d64k");
                params.put("X-Goog-FieldMask", "places.displayName");
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("includedTypes", new JSONArray().put("subway_station"));
                    jsonBody.put("maxResultCount", 1);
                    jsonBody.put("rankPreference", "DISTANCE");
                    jsonBody.put("locationRestriction", new JSONObject().put("circle", new JSONObject().put("center", new JSONObject().put("latitude", latitude).put("longitude", longitude)).put("radius", 500.0)));
                    return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getClosestTrainStationCrowd(Context context, Resources resources, String trainStationName, final VolleyCallback callback) {
        String trainStationLine = null, trainStationCode = null;

        try {
            CSVReaderHeaderAware trainStationCsv = new CSVReaderHeaderAware(new InputStreamReader(resources.openRawResource(R.raw.trainstation)));
            String[] trainStationData = trainStationCsv.readNext();
            do {
                if (Objects.equals(trainStationData[1], trainStationName)) {
                    trainStationLine = trainStationData[2];
                    trainStationCode = trainStationData[0];
                }
                trainStationData = trainStationCsv.readNext();
            } while (trainStationLine == null);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        String url = "http://datamall2.mytransport.sg/ltaodataservice/PCDRealTime?TrainLine=" + trainStationLine;
        String finalTrainStationCode = trainStationCode;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray trainStationList = response.getJSONArray("value");

                        for (int i = 0; i < trainStationList.length(); i++) {
                            JSONObject trainStation = trainStationList.getJSONObject(i);
                            if (trainStation.getString("Station").equals(finalTrainStationCode))
                                callback.onSuccess(trainStation.getString("CrowdLevel"));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> Log.d("TEST", "Error: " + error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("AccountKey", "LIrSz0h7Rju2OSnhONz6jQ==");
                return params;
            }
        };

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getClosestCarParkCrowd(Context context, double longitude, double latitude, final VolleyCallback callback) {
        Location currentLocation = new Location("");
        currentLocation.setLongitude(longitude);
        currentLocation.setLatitude(latitude);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        String url = "http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray carParkList = response.getJSONArray("value");
                        double closestDistance = 500;
                        JSONObject closestCarPark = null;
                        Location location = new Location("");
                        String[] latlong;

                        for (int i = 0; i < carParkList.length(); i++) {
                            latlong = carParkList.getJSONObject(i).getString("Location").split(" ");
                            location.setLatitude(Double.parseDouble(latlong[0]));
                            location.setLongitude(Double.parseDouble(latlong[1]));

                            if (currentLocation.distanceTo(location) < closestDistance) {
                                closestCarPark = carParkList.getJSONObject(i);
                                closestDistance = currentLocation.distanceTo(location);
                            }
                        }

                        callback.onSuccess(closestCarPark == null ? null : closestCarPark.getString("AvailableLots"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> Log.d("TEST", "Error: " + error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("AccountKey", "LIrSz0h7Rju2OSnhONz6jQ==");
                return params;
            }
        };

        mRequestQueue.add(jsonObjectRequest);
    }
}
