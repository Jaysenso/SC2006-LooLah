package com.example.loolah.database;

import com.example.loolah.model.*;
import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseSetup {
    private FirebaseFirestore db;
    private CollectionReference toiletColRef;

    public DatabaseSetup() {
        db = FirebaseFirestore.getInstance();
        toiletColRef = db.collection("toilets");
    }

    public void setup() {
        // Central
        toiletColRef.add(new Toilet("AMK Bus Interchange", "53 Ang Mo Kio Avenue 3, S(569933)", 103.848705, 1.3691369, ToiletType.BUS_INTERCHANGE, ToiletDistrict.CENTRAL, true, true, true, true)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
    }
}
