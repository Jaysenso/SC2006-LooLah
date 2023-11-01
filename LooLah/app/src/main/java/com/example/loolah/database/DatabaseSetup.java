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
        setupSouthWest();
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

    public void setupSouthWest(){
        toiletColRef.add(new Toilet("Boon Lay Bus Interchange","87 Jurong West Central 3, S(648343)",103.705742,1.3400445,ToiletType.BUS_INTERCHANGE, ToiletDistrict.SOUTH_WEST, true,true,true,true)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Kopitiam @ 450 Clementi Avenue 3","450 Clementi Avenue 3, #01-271, S(120450)",103.765368,1.3136967,ToiletType.COFFEESHOP, ToiletDistrict.SOUTH_WEST, true,true,false,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Unison Construction Dormitory","6 Tuas Basin Link, S(638760)",103.6484818,1.3184954,ToiletType.DORMITORY, ToiletDistrict.SOUTH_WEST, false,true,false,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Kopitiam","1 Lower Kent Ridge Road, #05-01 One@Kent Ridge, S (119082)",103.784831,1.2938782,ToiletType.FOOD_COURT, ToiletDistrict.SOUTH_WEST, true,true,true,true)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("CleanTech One","CleanTech One, 1 Clean Tech Loop, S(637141)",103.6921728,1.3550366,ToiletType.INDUSTRIAL_COMPLEX, ToiletDistrict.SOUTH_WEST, true,true,true,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Ayer Rajah Amenity Centre","69 Ayer Rajah Crescent, S(139961)",103.787009,1.296167,ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.SOUTH_WEST, true,true,true,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Beauty World MRT Station (DT5)","101 Upper Bukit Timah Road, S(588216)",103.7758127,1.3408774,ToiletType.MRT_STATION, ToiletDistrict.SOUTH_WEST, true,true,false,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("Jurong Eco Garden","1 Cleantech Loop Singapore, S(637141)",103.6921766,1.3553891,ToiletType.PARK, ToiletDistrict.SOUTH_WEST, true,true,true,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet("CKR Contract Services Pte Ltd","56 Sungei Kadut Drive, S(729573)",103.7449088,1.4126232,ToiletType.PRIVATE_OFFICE, ToiletDistrict.SOUTH_WEST, true,true,false,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
        toiletColRef.add(new Toilet(" Choa Chu Kang Centre","309 Choa Chu Kang Avenue 4, S(680308)",103.742769,1.385547,ToiletType.SHOPPING_CENTRE, ToiletDistrict.SOUTH_WEST, true,true,true,false)).addOnSuccessListener(documentReference -> toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId()));
    }
}
