package com.example.loolah.util;

import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;

import java.util.Arrays;
import java.util.stream.Stream;

public class SpinnerUtil {
    private static String[] distance = new String[]{"Distance", "< 100m", "< 500m", "< 1km", "< 3km", "< 5km"};
    private static double[] distanceValues = new double[]{5000, 100, 500, 1000, 3000, 5000};

    private static String[] rating = new String[]{"Rating", "1 star", "2 star", "3 star", "4 star", "5 star"};
    private static int[] ratingValues = new int[]{0, 1, 2, 3, 4, 5};

    private static String[] district = Stream.concat(Arrays.stream(new String[]{"District"}), Arrays.stream(ToiletDistrict.values()).map(ToiletDistrict::getToiletDistrict)).toArray(String[]::new);
    private static ToiletDistrict[] districtValues = ToiletDistrict.values();

    private static String[] type = Stream.concat(Arrays.stream(new String[]{"Type"}), Arrays.stream(ToiletType.values()).map(ToiletType::getToiletType)).toArray(String[]::new);
    private static ToiletType[] typeValues = ToiletType.values();

    public static String[] getDistance() {
        return distance;
    }

    public static double getDistanceValue(int position) {
        return distanceValues[position];
    }

    public static String[] getRating() {
        return rating;
    }

    public static int getRatingValue(int position) {
        return ratingValues[position];
    }

    public static String[] getDistrict() {
        return district;
    }

    public static ToiletDistrict getDistrictValue(int position) {
        if (position == 0) return null;
        return districtValues[position - 1];
    }

    public static String[] getType() {
        return type;
    }

    public static ToiletType getTypeValue(int position) {
        if (position == 0) return null;
        return typeValues[position - 1];
    }
}
