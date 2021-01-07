package com.glasgowuniversity.assay;

public class Manufacturer {

    private static String manufacturerID;

    private static String manufacturerUsername;

    public static String getManufacturerID() {
        return manufacturerID;
    }

    public static void setManufacturerID(String manufacturerID) {
        Manufacturer.manufacturerID = manufacturerID;
    }

    public static void reset(){
        manufacturerID = "";
        manufacturerUsername = "";
    }

    public static String getManufacturerUsername() {
        return manufacturerUsername;
    }

    public static void setManufacturerUsername(String manufacturerUsername) {
        Manufacturer.manufacturerUsername = manufacturerUsername;
    }
}
