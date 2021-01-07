package com.glasgowuniversity.assay;

import android.net.Uri;

public class AssayDevice {
    private static String deviceId = "";

    private static boolean deviceTest;

    private static boolean devicePost;

    private static String testName;

    private static String manufacture;

    private static String dateOfManufacture;

    private static String expireDate;

    private static String benchNumber;

    private static String productionPlace;

    private static String status;

    private static String operator;

    private static String testDate;

    private static String patientId;

    private static String dateOfBirth;

    private static String gender;

    private static String weight;

    private static String url;

    private static String result;

    private static String testPlace;

    private static Uri imageUri;

    public static String getDeviceId() {
        return deviceId;
    }

    public static void setDeviceTest(boolean state) {
        deviceTest = state;
    }

    public static boolean isDeviceTest() {
        return deviceTest;
    }

    public static String getTestName() {
        return testName;
    }

    public static void setTestName(String testName) {
        AssayDevice.testName = testName;
    }

    public static String getManufacture() {
        return manufacture;
    }

    public static void setManufacture(String manufacture) {
        AssayDevice.manufacture = manufacture;
    }

    public static String getDateOfManufacture() {
        return dateOfManufacture;
    }

    public static void setDateOfManufacture(String dateOfManufacture) {
        AssayDevice.dateOfManufacture = dateOfManufacture;
    }

    public static String getExpireDate() {
        return expireDate;
    }

    public static void setExpireDate(String expireDate) {
        AssayDevice.expireDate = expireDate;
    }

    public static String getBenchNumber() {
        return benchNumber;
    }

    public static void setBenchNumber(String benchNumber) {
        AssayDevice.benchNumber = benchNumber;
    }

    public static String getProductionPlace() {
        return productionPlace;
    }

    public static void setProductionPlace(String productionPlace) {
        AssayDevice.productionPlace = productionPlace;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        AssayDevice.status = status;
    }

    public static String getOperator() {
        return operator;
    }

    public static void setOperator(String operator) {
        AssayDevice.operator = operator;
    }

    public static String getTestDate() {
        return testDate;
    }

    public static void setTestDate(String testDate) {
        AssayDevice.testDate = testDate;
    }

    public static String getPatientId() {
        return patientId;
    }

    public static void setPatientId(String patientId) {
        AssayDevice.patientId = patientId;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        AssayDevice.url = url;
    }

    public static String getTestPlace() {
        return testPlace;
    }

    public static void setTestPlace(String testPlace) {
        AssayDevice.testPlace = testPlace;
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static void setDateOfBirth(String dateOfBirth) {
        AssayDevice.dateOfBirth = dateOfBirth;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        AssayDevice.gender = gender;
    }

    public static String getWeight() {
        return weight;
    }

    public static void setWeight(String weight) {
        AssayDevice.weight = weight;
    }

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        AssayDevice.result = result;
    }

    public static void setDeviceId(String id) {
        deviceId = id;
    }

    public static Uri getImageUri() {
        return imageUri;
    }

    public static void setImageUri(Uri uri) {
        imageUri = uri;
    }

    public static void resetDevice(){
        deviceId = "";

        deviceTest = false;

        testName = "";

        manufacture = "";

        dateOfManufacture = "";

        expireDate = "";

        benchNumber = "";

        productionPlace = "";

        status = "";

        operator = "";

        testDate = "";

        patientId = "";

        dateOfBirth = "";

        gender = "";

        weight = "";

        url = "";

        result = "";

        testPlace = "";

        imageUri = null;
    }

    public static boolean isDevicePost() {
        return devicePost;
    }

    public static void setDevicePost(boolean devicePost) {
        AssayDevice.devicePost = devicePost;
    }
}
