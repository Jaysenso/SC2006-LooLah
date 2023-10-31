package com.example.loolah.model.enums;

public enum ToiletType {
    BUS_INTERCHANGE ("Bus Interchange"),
    CLUB ("Club"),
    COFFEESHOP ("Coffeeshop"),
    GOVERNMENT_OFFICE ("Government Office"),
    MARKET_FOOD_CENTRE ("Market & Food Centre"),
    MRT_STATION ("MRT Station"),
    PARK ("Park"),
    PIER ("Pier"),
    PLACE_OF_WORSHIP ("Place of worship"),
    PRIVATE_OFFICE ("Private Office"),
    RESTAURANT ("Restaurant"),
    SHOPPING_CENTRE ("Shopping Centre"),
    TOURIST_ATTRACTION ("Tourist Attraction"),
    COMMUNITY_CENTRE ("Community Centre"),
    FOOD_COURT ("Food Court"),
    DORMITORY ("Dormitory"),
    INDUSTRIAL_COMPLEX ("Industrial Complex");

    private final String toiletTypeStr;

    ToiletType(String toiletTypeStr) {
        this.toiletTypeStr = toiletTypeStr;
    }

    public String getToiletType() {
        return toiletTypeStr;
    }
}
