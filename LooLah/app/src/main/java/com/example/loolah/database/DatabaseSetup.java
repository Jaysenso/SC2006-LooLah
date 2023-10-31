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
        setupCentral();
    }

    public void setupCentral() {
        toiletColRef.add(new Toilet("AMK Bus Interchange", "53 Ang Mo Kio Avenue 3, S(569933)", 103.848705, 1.3691369, ToiletType.BUS_INTERCHANGE, ToiletDistrict.CENTRAL, true, true, true, true)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Seletar Country Club", "101 Seletar Club Road, S(798273)", 103.8581298, 1.4096081, ToiletType.CLUB, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Kopitiam @ 437 Fernvale Road", "437 Fernvale Road, #01-01, S(790437)", 103.8761231, 1.3926519, ToiletType.COFFEESHOP, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("NTUC Foodfare Court@Clifford Centre", "Clifford Centre, 24 Raffles Place, S(048621)", 103.8519697, 1.2838533, ToiletType.FOOD_COURT, ToiletDistrict.CENTRAL, true, true, false, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Maritime and Port Authority of Singapore", "460 Alexandra Road, #18-00, S(119963)", 103.8015216, 1.2736761, ToiletType.GOVERNMENT_OFFICE, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("112 Bukit Merah Market & Food Centre", "Blk 112 Jalan Bukit Merah, S(160112)", 103.8260044, 1.2800033, ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Ang Mo Kio MRT station", "2450 Ang Mo Kio Avenue 8, S(569811)", 103.8486932, 1.3707675, ToiletType.MRT_STATION, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("The Oval Seletar Aerospace Park", "3 Park Lane, S(798387)", 103.8683218, 1.408043, ToiletType.PARK, ToiletDistrict.CENTRAL, true, true, true, true)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Marina South Pier", "31 Marina Coastal Drive, S(018988)", 103.8632543, 1.2708997, ToiletType.PIER, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("St.George's Church", "44 Minden Road, S(248816)", 103.815153, 1.304811, ToiletType.PLACE_OF_WORSHIP, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Lum Chang Building", "14, Kung Chong Road, S(159150)", 103.8112565, 1.2900376, ToiletType.PRIVATE_OFFICE, ToiletDistrict.CENTRAL, true, true, false, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Buangkok Square Mall", "991 Buangkok Link, Buangkok Square Mall, S(530991)", 103.8819586, 1.3846918, ToiletType.SHOPPING_CENTRE, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("ArBora Cafe", "109 Mount Faber Road, Level 2, S(099230)", 103.8192945, 1.2713774, ToiletType.TOURIST_ATTRACTION, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
    }
}
