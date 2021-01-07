package com.glasgowuniversity.assay;

public class Admin {

    public static void setAdminId(String adminId) {
        Analyst.setAnalystID(adminId);
        Manufacturer.setManufacturerID(adminId);
        Operator.setOperatorID(adminId);
    }

    public static void setAdminUsername(String adminUsername) {
        Analyst.setAnalystUsername(adminUsername);
        Manufacturer.setManufacturerUsername(adminUsername);
        Operator.setOperatorUsername(adminUsername);
    }

    public static void reset(){
        Analyst.reset();
        Manufacturer.reset();
        Operator.reset();

    }
}
