package com.glasgowuniversity.assay;

public class Operator {

    private static  String operatorID;

    private static String operatorUsername;

    public static String getOperatorID() {
        return operatorID;
    }

    public static void setOperatorID(String operatorID) {
        Operator.operatorID = operatorID;
    }

    public static String getOperatorUsername() {
        return operatorUsername;
    }

    public static void setOperatorUsername(String operatorUsername) {
        Operator.operatorUsername = operatorUsername;
    }

    public static void reset(){
        operatorID = "";
        operatorUsername = "";
    }

}
