package com.glasgowuniversity.assay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.format;

public class DevInfoManufacturerActivity extends AppCompatActivity {


    private TextView manufacturerId;
    private TextView deviceId;
    private EditText testName;
    private EditText dateOfManufacture;
    private EditText expireDate;
    private EditText benchNumber;
    private EditText productionPlace;
    private Spinner status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info_manufacturer);

        manufacturerId = findViewById(R.id.dev_info_manufacturer_label_user_id);
        deviceId = findViewById(R.id.dev_info_manufacturer_label_dev_id);
        testName = findViewById(R.id.dev_info_manufacturer_input_test_name);

        dateOfManufacture = findViewById(R.id.dev_info_manufacturer_input_manufacture_date);
        expireDate = findViewById(R.id.dev_info_manufacturer_input_expiration_date);
        benchNumber = findViewById(R.id.dev_info_manufacturer_input_bench_number);
        productionPlace = findViewById(R.id.dev_info_manufacturer_input_producing_area);
        status = findViewById(R.id.dev_info_manufacturer_status_spinner);

        manufacturerId.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_user_id),
                Manufacturer.getManufacturerID(), LoginActivity.getStatus())));

        findViewById(R.id.dev_info_manufacturer_button_scan_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssayDevice.setDeviceTest(false);
                AssayDevice.setDevicePost(true);
                startActivity(new Intent(DevInfoManufacturerActivity.this, QrScannerActivity.class));
            }
        });

        findViewById(R.id.dev_info_manufacturer_button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FeedTask().execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!AssayDevice.getDeviceId().isEmpty()){
            deviceId.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_dev_id),AssayDevice.getDeviceId())));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss.SSS");
            String timestamp = simpleDateFormat.format(new Date()) + "T" + simpleDateFormat1.format(new Date()) +"Z";
            dateOfManufacture.setText(timestamp);
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, 1);
            Date newDate = c.getTime();
            String timestamp1 =  simpleDateFormat.format(newDate) + "T" + simpleDateFormat1.format(newDate) +"Z";
            expireDate.setText(timestamp1);
        }
    }

    public class FeedTask extends AsyncTask<String,Void,String> {

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... Params){
            try {
                OkHttpClient client = new OkHttpClient();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss.SSS");
                String timestamp = simpleDateFormat.format(new Date()) + "T" + simpleDateFormat1.format(new Date()) +"Z";

                JSONObject m = new JSONObject();
                m.put("$class","org.assay.Manufacturer")
                        .put("manufacturerId", Manufacturer.getManufacturerID())
                        .put("userName", Manufacturer.getManufacturerUsername());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("$class","org.assay.ProduceDevice")
                        .put("testName",testName.getText().toString())
                        .put("manufacturer",m)
                        .put("dateOfManufacture",dateOfManufacture.getText().toString())
                        .put("expireDate",expireDate.getText().toString())
                        .put("benchNumber",benchNumber.getText().toString())
                        .put("productionPlace",productionPlace.getText().toString())
                        .put("status",String.valueOf(status.getSelectedItem()))
                        .put("device","org.assay.Device#"+AssayDevice.getDeviceId())
                        .put("deviceId",AssayDevice.getDeviceId())
                        .put("timestamp", timestamp);


                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),String.valueOf(jsonObject));

                Log.d("JSonBody:", String.valueOf(jsonObject));

//                        .url("http://35.246.145.147:3001/api/ProduceDevice"+"?access_token="+token)
                Request request = new Request.Builder()
                        .url("http://34.107.60.133:"+LoginActivity.port+"/api/ProduceDevice")
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                String result = response.body().string();
                Log.d("JSonBody:", result);
                return result;
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s==null){
                Toast.makeText(getApplicationContext(),"Failed to connect\nPlease try again later", Toast.LENGTH_LONG).show();
                return;
            }

            if(!s.isEmpty() && s.toLowerCase().contains("error")) Toast.makeText(getApplicationContext(),"Failed to Save: " + s,Toast.LENGTH_LONG).show();

            if(!s.isEmpty() && !s.toLowerCase().contains("error")){
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                AssayDevice.setDevicePost(false);
                startActivity(new Intent(DevInfoManufacturerActivity.this,MainActivity.class));
            }
        }
    }
}
