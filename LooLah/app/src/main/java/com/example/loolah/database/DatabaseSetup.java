package com.example.loolah.database;

import android.content.Context;

import com.example.loolah.model.*;
import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseSetup {
    private final CollectionReference toiletColRef;
    private final StorageReference storageRef;

    public DatabaseSetup() {
        toiletColRef = FirebaseFirestore.getInstance().collection("toilets");
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void setup(Context context) {
        setupCentral(context);
        setUpNorthWest(context);
        setupSouthWest();
        setupNorthEast(context);
        setupSouthEast(context);
    }

    public void setupCentral(Context context) {
        toiletColRef.add(new Toilet("AMK Bus Interchange", "53 Ang Mo Kio Avenue 3, S(569933)", 103.848705, 1.3691369, ToiletType.BUS_INTERCHANGE, ToiletDistrict.CENTRAL, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "AMK Bus Interchange", 5);
        });
        toiletColRef.add(new Toilet("Seletar Country Club", "101 Seletar Club Road, S(798273)", 103.8581298, 1.4096081, ToiletType.CLUB, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Seletar Country Club", 5);
        });
        toiletColRef.add(new Toilet("Kopitiam @ 437 Fernvale Road", "437 Fernvale Road, #01-01, S(790437)", 103.8761231, 1.3926519, ToiletType.COFFEESHOP, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Kopitiam @ 437 Fernvale Road", 5);
        });
        toiletColRef.add(new Toilet("NTUC Foodfare Court@Clifford Centre", "Clifford Centre, 24 Raffles Place, S(048621)", 103.8519697, 1.2838533, ToiletType.FOOD_COURT, ToiletDistrict.CENTRAL, true, true, false, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "NTUC Foodfare Court@Clifford Centre", 5);
        });
        toiletColRef.add(new Toilet("Maritime and Port Authority of Singapore", "460 Alexandra Road, #18-00, S(119963)", 103.8015216, 1.2736761, ToiletType.GOVERNMENT_OFFICE, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Maritime and Port Authority of Singapore", 5);
        });
        toiletColRef.add(new Toilet("Bukit Merah Market & Food Centre", "Blk 112 Jalan Bukit Merah, S(160112)", 103.8260044, 1.2800033, ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Bukit Merah Market & Food Centre", 5);
        });
        toiletColRef.add(new Toilet("Ang Mo Kio MRT station", "2450 Ang Mo Kio Avenue 8, S(569811)", 103.8486932, 1.3707675, ToiletType.MRT_STATION, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Ang Mo Kio MRT station", 5);
        });
        toiletColRef.add(new Toilet("The Oval Seletar Aerospace Park", "3 Park Lane, S(798387)", 103.8683218, 1.408043, ToiletType.PARK, ToiletDistrict.CENTRAL, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "The Oval Seletar Aerospace Park", 5);
        });
        toiletColRef.add(new Toilet("Marina South Pier", "31 Marina Coastal Drive, S(018988)", 103.8632543, 1.2708997, ToiletType.PIER, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Marina South Pier", 5);
        });
        toiletColRef.add(new Toilet("St.George's Church", "44 Minden Road, S(248816)", 103.815153, 1.304811, ToiletType.PLACE_OF_WORSHIP, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "St.George's Church", 5);
        });
        toiletColRef.add(new Toilet("Lum Chang Building", "14, Kung Chong Road, S(159150)", 103.8112565, 1.2900376, ToiletType.PRIVATE_OFFICE, ToiletDistrict.CENTRAL, true, true, false, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Lum Chang Building", 5);
        });
        toiletColRef.add(new Toilet("Buangkok Square Mall", "991 Buangkok Link, Buangkok Square Mall, S(530991)", 103.8819586, 1.3846918, ToiletType.SHOPPING_CENTRE, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Buangkok Square Mall", 5);
        });
        toiletColRef.add(new Toilet("ArBora Cafe", "109 Mount Faber Road, Level 2, S(099230)", 103.8192945, 1.2713774, ToiletType.TOURIST_ATTRACTION, ToiletDistrict.CENTRAL, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "ArBora Cafe", 5);
        });
    }

    public void setupSouthWest() {
        toiletColRef.add(new Toilet("Boon Lay Bus Interchange", "87 Jurong West Central 3, S(648343)", 103.705742, 1.3400445, ToiletType.BUS_INTERCHANGE, ToiletDistrict.SOUTH_WEST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Boon Lay Bus Interchange", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/4S_BusInterchangeBoonLay_3.jpg", "https://www.toilet.org.sg/photos/4S_BusInterchangeBoonLay_7.jpg")));
        });
        toiletColRef.add(new Toilet("Kopitiam @ 450 Clementi Avenue 3", "450 Clementi Avenue 3, #01-271, S(120450)", 103.765368, 1.3136967, ToiletType.COFFEESHOP, ToiletDistrict.SOUTH_WEST, true, true, false, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Kopitiam @ 450 Clementi Avenue 3", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/3S-Kopitiam450Clementi-5.jpg", "https://www.toilet.org.sg/photos/3S-Kopitiam450Clementi-6.jpg")));
        });
        toiletColRef.add(new Toilet("Unison Construction Dormitory", "6 Tuas Basin Link, S(638760)", 103.6484818, 1.3184954, ToiletType.DORMITORY, ToiletDistrict.SOUTH_WEST, false, true, false, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Unison Construction Dormitory", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/5S-UnisonDorms-2.jpg", "https://www.toilet.org.sg/photos/5S-UnisonDorms-4.jpg")));
        });
        toiletColRef.add(new Toilet("Kopitiam", "1 Lower Kent Ridge Road, #05-01 One@Kent Ridge, S (119082)", 103.784831, 1.2938782, ToiletType.FOOD_COURT, ToiletDistrict.SOUTH_WEST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Kopitiam", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/5S_NUHKopitiam_9.jpg", "https://www.toilet.org.sg/photos/5S_NUHKopitiam_13.jpg")));
        });
        toiletColRef.add(new Toilet("CleanTech One", "CleanTech One, 1 Clean Tech Loop, S(637141)", 103.6921728, 1.3550366, ToiletType.INDUSTRIAL_COMPLEX, ToiletDistrict.SOUTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "CleanTech One", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/5S-CleanTechTwo_3.jpg", "https://www.toilet.org.sg/photos/5S-CleanTechTwo_5.jpg")));
        });
        toiletColRef.add(new Toilet("Ayer Rajah Amenity Centre", "69 Ayer Rajah Crescent, S(139961)", 103.787009, 1.296167, ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.SOUTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Ayer Rajah Amenity Centre", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/4sAyerRajahAmenityC_3.jpg", "https://www.toilet.org.sg/photos/4sAyerRajahAmenityC_8.jpg")));
        });
        toiletColRef.add(new Toilet("Beauty World MRT Station (DT5)", "101 Upper Bukit Timah Road, S(588216)", 103.7758127, 1.3408774, ToiletType.MRT_STATION, ToiletDistrict.SOUTH_WEST, true, true, false, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Beauty World MRT Station (DT5)", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/4S_BeautyWorldMRT_4.jpg", "https://www.toilet.org.sg/photos/4S_BeautyWorldMRT_6.jpg")));
        });
        toiletColRef.add(new Toilet("Jurong Eco Garden", "1 Cleantech Loop Singapore, S(637141)", 103.6921766, 1.3553891, ToiletType.PARK, ToiletDistrict.SOUTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Jurong Eco Garden", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/5S_JurongEco_7.jpg", "https://www.toilet.org.sg/photos/5S_JurongEco_11.jpg")));
        });
        toiletColRef.add(new Toilet("CKR Contract Services Pte Ltd", "56 Sungei Kadut Drive, S(729573)", 103.7449088, 1.4126232, ToiletType.PRIVATE_OFFICE, ToiletDistrict.SOUTH_WEST, true, true, false, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "CKR Contract Services Pte Ltd", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/3S_CKRContract_5.jpg", "https://www.toilet.org.sg/photos/3S_CKRContract_6.jpg")));
        });
        toiletColRef.add(new Toilet("Choa Chu Kang Centre", "309 Choa Chu Kang Avenue 4, S(680308)", 103.742769, 1.385547, ToiletType.SHOPPING_CENTRE, ToiletDistrict.SOUTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromUrl(documentReference.getId(), "Choa Chu Kang Centre", new ArrayList<>(Arrays.asList("https://www.toilet.org.sg/photos/4S_CCKCentre_7.jpg", "https://www.toilet.org.sg/photos/3S_CKRContract_6.jpg")));
        });
    }

    public void setUpNorthWest(Context context) {
        toiletColRef.add(new Toilet("Bukit Panjang Integrated Transport Hub", "15 Petir Road, S(678270)", 103.7638759, 1.3784446, ToiletType.BUS_INTERCHANGE, ToiletDistrict.NORTH_WEST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Bukit Panjang Integrated Transport Hub", 5);
        });
        toiletColRef.add(new Toilet("Kopitiam @ 504 Yishun Street 51", "504 Yishun Street 51, #01-01, S(760504)", 103.8445869, 1.4182345, ToiletType.COFFEESHOP, ToiletDistrict.NORTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Kopitiam @ 504 Yishun Street 51", 5);
        });
        toiletColRef.add(new Toilet("Bukit Canberra Indoor Sports Hall", "21 Canberra Link, S(756973)", 103.8236415, 1.4488532, ToiletType.COMMUNITY_CENTRE, ToiletDistrict.NORTH_WEST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Bukit Canberra Indoor Sports Hall", 5);
        });
        toiletColRef.add(new Toilet("NTUC Foodfare KTPH", "90 Yishun Central, S(768828)", 103.8385907, 1.4242972, ToiletType.FOOD_COURT, ToiletDistrict.NORTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "NTUC Foodfare KTPH", 5);
        });
        toiletColRef.add(new Toilet("Chong Pang Market & Food Centre", "105 Yishun Ring Road, S(760105)", 103.8280566, 1.4317981, ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.NORTH_WEST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Chong Pang Market & Food Centre", 5);
        });
        toiletColRef.add(new Toilet("Cashew MRT Station", "1 Cashew Road, S(679696)", 103.7645535, 1.3699296, ToiletType.MRT_STATION, ToiletDistrict.NORTH_WEST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Cashew MRT Station", 5);
        });
        toiletColRef.add(new Toilet("Sembawang Mart", "Blk 511 Canberra Road, S(750511)", 103.8195883, 1.4538337, ToiletType.SHOPPING_CENTRE, ToiletDistrict.NORTH_WEST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Sembawang Mart", 5);
        });

    }

    public void setupNorthEast(Context context) {
        toiletColRef.add(new Toilet("Sengkang Bus Interchange", "13 Sengkang Square, S(545077)", 103.8961290, 1.3924638, ToiletType.BUS_INTERCHANGE, ToiletDistrict.NORTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Sengkang Bus Interchange", 5);
        });
        toiletColRef.add(new Toilet("NTUC Club Downtown East", "1 Pasir Ris Close S(519599)", 103.9551183, 1.3778516, ToiletType.CLUB, ToiletDistrict.NORTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "NTUC Club Downtown East", 5);
        });
        toiletColRef.add(new Toilet("Kopitiam @ 275D Compassvale Link", "275D Compassvale Link, #01-01, S(544275)", 103.8937421, 1.3835105, ToiletType.COFFEESHOP, ToiletDistrict.NORTH_EAST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Kopitiam @ 275D Compassvale Link", 5);
        });
        toiletColRef.add(new Toilet("Serangoon Garden Market", "49A Serangoon Garden Way, S(555945)", 103.8665831, 1.3634822, ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.NORTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Serangoon Garden Market", 5);
        });
        toiletColRef.add(new Toilet("Eunos MRT Station", "30 Eunos Crescent, S(409423)", 103.9028833, 1.3199036, ToiletType.MRT_STATION, ToiletDistrict.NORTH_EAST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Eunos MRT Station", 5);
        });
        toiletColRef.add(new Toilet("Al - Istighfar Mosque", "2 Pasir Ris Walk, S(518239)", 103.9640656, 1.3713552, ToiletType.PLACE_OF_WORSHIP, ToiletDistrict.NORTH_EAST, true, true, true, false)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Al - Istighfar Mosque", 5);
        });
        toiletColRef.add(new Toilet("Century Square", "2 Tampines Central 5, S(529509)", 103.9436205, 1.3528328, ToiletType.SHOPPING_CENTRE, ToiletDistrict.NORTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Century Square", 5);
        });
    }

    public void setupSouthEast(Context context){
        toiletColRef.add(new Toilet("Hearbeat@Bedok", "11 Bedok North Street 1, S(469662)", 1.3270, 103.9321, ToiletType.GOVERNMENT_OFFICE, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Hearbeat@Bedok", 5);
        });

        toiletColRef.add(new Toilet("Changi Business Park 15", "15 Changi Business Park Central 1, S(486057)", 1.336466, 103.968533, ToiletType.PRIVATE_OFFICE, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Changi Business Park 15", 5);
        });

        toiletColRef.add(new Toilet("Kopitiam @ 248 Simei Street 3", "238 Simei Street 3, #01-136, S(520248)", 1.341699, 103.952998, ToiletType.COFFEESHOP, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Kopitiam @ 248 Simei Street 3", 5);
        });

        toiletColRef.add(new Toilet("Kopitiam Corner", "152A Bedok South Road, S(461152)", 1.317732, 103.947769, ToiletType.FOOD_COURT, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), " Kopitiam Corner", 1);
        });

        toiletColRef.add(new Toilet("Eunos Bus Interchange", "409 Eunos Road 2, S(409388)", 1.319421, 103.902488, ToiletType.BUS_INTERCHANGE, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Eunos Bus Interchange", 5);
        });

        toiletColRef.add(new Toilet("Changi Village Hawker Centre", "2 Changi Village Road, S(500002)", 1.389146, 103.988316, ToiletType.MARKET_FOOD_CENTRE, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Changi Village Hawker Centre", 5);
        });

        toiletColRef.add(new Toilet("Paya Lebar MRT Station", "30 Paya Lebar Road, S(409006)", 1.318271, 103.893283, ToiletType.MRT_STATION, ToiletDistrict.SOUTH_EAST, true, true, true, true)).addOnSuccessListener(documentReference -> {
            toiletColRef.document(documentReference.getId()).update("toiletId", documentReference.getId());
            uploadToiletImagesFromFile(context, documentReference.getId(), "Paya Lebar MRT Station", 5);
        });
    }

    public void uploadToiletImagesFromFile(Context context, String toiletId, String toiletName, int numOfPics) {
        String fileName = toiletName.replaceAll("\\s@?&?\\s?|@|\\.|'", "_").toLowerCase();
        InputStream is;

        for (int i = 1; i <= numOfPics; i++) {
            is = context.getResources().openRawResource(context.getResources().getIdentifier((fileName + "_" + i), "raw", context.getPackageName()));
            StorageReference toiletImgUrlRef = storageRef.child("images/toilet/" + fileName + "_" + i);
            toiletImgUrlRef.putStream(is).addOnFailureListener(Throwable::printStackTrace).addOnSuccessListener(taskSnapshot -> toiletImgUrlRef.getDownloadUrl().addOnSuccessListener(uri -> toiletColRef.document(toiletId).update("photoUrl", FieldValue.arrayUnion(uri.toString()))));
        }
    }

    public void uploadToiletImagesFromUrl(String toiletId, String toiletName, ArrayList<String> toiletImgUrl) {
        String fileName = toiletName.replaceAll("\\s@?&?\\s?|@|\\.|'", "_").toLowerCase();

        new Thread(() -> {
            URL url;
            HttpURLConnection connection;

            for (int i = 1; i <= toiletImgUrl.size(); i++) {
                try {
                    url = new URL(toiletImgUrl.get(i - 1));
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    StorageReference toiletImgUrlRef = storageRef.child("images/toilet/" + fileName + "_" + i);
                    toiletImgUrlRef.putStream(is).addOnFailureListener(Throwable::printStackTrace).addOnSuccessListener(taskSnapshot -> toiletImgUrlRef.getDownloadUrl().addOnSuccessListener(uri -> toiletColRef.document(toiletId).update("photoUrl", FieldValue.arrayUnion(uri.toString()))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
