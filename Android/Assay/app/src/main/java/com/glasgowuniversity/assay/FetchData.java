package com.glasgowuniversity.assay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchData extends AsyncTask <Object,Void,Object> {

    private String urlPath = QrScannerActivity.urlPath;

    @SuppressLint("StaticFieldLeak")
    Context context = QrScannerActivity.context;


    @Override
    protected Object doInBackground(Object[] objects) {
        OkHttpClient client = new OkHttpClient();

//        .url("http://35.198.105.18:3000/api/"+urlPath+"?access_token="+token)

        Request request =  new Request.Builder()
                .url("http://34.107.60.133:"+LoginActivity.port+"/api/"+urlPath)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();

        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        if(o == null){
            Toast.makeText(context,"Failed to connect\nPlease try again later",Toast.LENGTH_LONG).show();
            return;
        }

        if(!o.toString().isEmpty() && o.toString().contains("error") && o.toString().contains("404")){
            Toast.makeText(context,"Device not found",Toast.LENGTH_LONG).show();
            return;
        }

        if(!o.toString().isEmpty() && !o.toString().contains("error")){

            try {
                JSONObject deviceData = new JSONObject(String.valueOf(o));

                AssayDevice.setTestName(deviceData.getString("testName"));
                AssayDevice.setManufacture(deviceData.getJSONObject("manufacturer").getString("manufacturerId"));
                AssayDevice.setDateOfManufacture(deviceData.getString("dateOfManufacture"));
                AssayDevice.setExpireDate(deviceData.getString("expireDate"));
                AssayDevice.setBenchNumber(deviceData.getString("benchNumber"));
                AssayDevice.setProductionPlace(deviceData.getString("productionPlace"));
                AssayDevice.setStatus(deviceData.getString("status"));
                AssayDevice.setOperator(deviceData.getJSONObject("operator").getString("operatorId"));
                AssayDevice.setTestDate(deviceData.getString("testDate"));
                AssayDevice.setPatientId(deviceData.getString("patientId"));
                AssayDevice.setDateOfBirth(deviceData.getString("dateOfBirth"));
                AssayDevice.setGender(deviceData.getString("gender"));
                AssayDevice.setWeight(deviceData.getString("weight"));
                AssayDevice.setUrl(deviceData.getString("url"));
                AssayDevice.setResult(deviceData.getString("result"));
                AssayDevice.setTestPlace(deviceData.getString("testPlace"));

                if(!LoginActivity.getStatus().equals("Manufacturer") && !AssayDevice.isDevicePost()) DevInfoAnalystActivity.update();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
