package com.glasgowuniversity.assay;



public class Analyst {

    private static String analystID;

    private static String analystUsername;


    public static String getAnalystID() {
        return analystID;
    }

    public static void setAnalystID(String analystID) {
        Analyst.analystID = analystID;
    }

    public static String getAnalystUsername() {
        return analystUsername;
    }

    public static void setAnalystUsername(String analystUsername) {
        Analyst.analystUsername = analystUsername;
    }

    public static void reset(){
        analystID = "";
        analystUsername = "";
    }
}
