package com.example.loolah.model.enums;

public enum ToiletDistrict {
    CENTRAL ("Central"),
    NORTH_EAST ("North East"),
    NORTH_WEST ("North West"),
    SOUTH_EAST ("South East"),
    SOUTH_WEST ("South West");

    private final String toiletDistrictStr;

    ToiletDistrict(String toiletDistrictStr) {
        this.toiletDistrictStr = toiletDistrictStr;
    }

    public String getToiletDistrict() {
        return toiletDistrictStr;
    }
}
