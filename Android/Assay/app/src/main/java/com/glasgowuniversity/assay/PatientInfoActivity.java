package com.glasgowuniversity.assay;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.format;

public class PatientInfoActivity extends AppCompatActivity implements LocationListener {

    private TextView deviceId;
    private Spinner status;
    private EditText testDate;
    private EditText patientId;
    private Spinner gender;
    private Spinner weight;
    private TextView img_url;
    private TextView result;
    private TextView testPlace;
    private Spinner dateOfBirth;
//    private EditText childName;
//    private EditText fatherName;

    private Button save;

    protected static double location_lat;
    protected static double location_lng;
    private static String resultLabel;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        deviceId = findViewById(R.id.patient_info_label_dev_id);
        status = findViewById(R.id.patient_info_status_spinner);
        testDate = findViewById(R.id.patient_info_input_test_date);
        patientId = findViewById(R.id.patient_info_input_patient_id);
        gender = findViewById(R.id.patient_info_gender_spinner);
        weight = findViewById(R.id.patient_info_input_weight_spinner);
        img_url = findViewById(R.id.patient_info_label_img_url);
        result = findViewById(R.id.patient_info_input_result);
        testPlace = findViewById(R.id.patient_info_input_location);
        dateOfBirth = findViewById(R.id.patient_info_input_age_spinner);
//        childName = findViewById(R.id.patient_info_input_child_name);
//        fatherName = findViewById(R.id.patient_info_input_father_name);

        save = findViewById(R.id.patient_info_button_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String data = "{" + "Child Name:" + childName.getText().toString() + ", Father's Name:" + fatherName.getText().toString() +"}";
//                writeToDatabase(data);
                Toast.makeText(getApplicationContext(),"UPLOADING . . . ", Toast.LENGTH_LONG).show();
                new FeedTask().execute();
            }
        });
        img_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FirebasePhoto = new Intent(Intent.ACTION_VIEW, AssayDevice.getImageUri());
                startActivity(FirebasePhoto);
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        testPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.setTestLocation(location_lat, location_lng);
                startActivity(new Intent(PatientInfoActivity.this, MapsActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!AssayDevice.getDeviceId().isEmpty()){
            deviceId.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_dev_id),AssayDevice.getDeviceId())));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss.SSS");
            String timestamp = simpleDateFormat.format(new Date()) + "T" + simpleDateFormat1.format(new Date()) +"Z";
            testDate.setText(timestamp);
            SimpleDateFormat simpleDateString = new SimpleDateFormat("yyyyMMdd");
            patientId.setText(simpleDateString.format(new Date()));
            img_url.setText(Html.fromHtml(format("<b>%s:</b> %s", getString(R.string.label_img_url),String.valueOf(AssayDevice.getImageUri()))));
            result.setText(resultLabel);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        result.setText(resultLabel);
    }

    @Override
    public void onLocationChanged(Location location) {
        location_lat = location.getLatitude();
        location_lng = location.getLongitude();
//        testPlace.setText(String.format("Latitude: %s, Longitude: %s", location.getLatitude(), location.getLongitude()));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class FeedTask extends AsyncTask<String,Void,String> {

        String s1 = String.valueOf(status.getSelectedItem());
        String s2 = testDate.getText().toString();
        String s3 = patientId.getText().toString();
        String s4 = String.valueOf(dateOfBirth.getSelectedItem());
        String s5 = String.valueOf(gender.getSelectedItem());
        String s6 = String.valueOf(weight.getSelectedItem());
        String s7 = result.getText().toString();
        String s8 = testPlace.getText().toString();

        @Override
        protected String doInBackground(String... Params){
            try {
                OkHttpClient client = new OkHttpClient();

                JSONObject o = new JSONObject();
                o.put("$class","org.assay.Operator")
                        .put("operatorId", Operator.getOperatorID())
                        .put("userName", Operator.getOperatorUsername());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("$class","org.assay.DoTheTest")
                        .put("status", s1)
                        .put("operator",o)
                        .put("testDate", s2)
                        .put("patientId", s3)
                        .put("dateOfBirth", s4)
                        .put("gender", s5)
                        .put("weight", s6)
                        .put("url", String.valueOf(AssayDevice.getImageUri()))
                        .put("result", s7)
                        .put("testPlace", s8)
                        .put("device","resource:org.assay.Device#"+AssayDevice.getDeviceId())
                        .put("deviceId",AssayDevice.getDeviceId());


                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),String.valueOf(jsonObject));

//                        .url("http://35.246.145.147:3001/api/DoTheTest"+"?access_token="+token)

                Request request = new Request.Builder()
                        .url("http://34.107.60.133:"+LoginActivity.port+"/api/DoTheTest")
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
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

            Log.d("PatientPost",s);

            if(!s.isEmpty() && s.toLowerCase().contains("error")) Toast.makeText(getApplicationContext(),"Failed to Save: " + s,Toast.LENGTH_LONG).show();

            if(!s.isEmpty() && !s.toLowerCase().contains("error")){
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PatientInfoActivity.this, MainActivity.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PatientInfoActivity.this, MainActivity.class));
    }

    private void writeToDatabase(String data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String fileName = format("%s_%s_%s", AssayDevice.getPatientId(), AssayDevice.getDeviceId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        DatabaseReference myRef = database.getReference("data/"+fileName);
        myRef.setValue(data);
    }

    public static void setResults(String data){
        String mlResults = "Please press 'ANALYSE' to get the results";

        if (data.contains("1P2N")) {
            mlResults = "pf+, pan-, brac+, pctrl-, nctrl-";
        } else if (data.contains("1N2P")) {
            mlResults = "pf-, pan+, brac+, pctrl-, nctrl-";
        } else if (data.contains("Double Positive")) {
            mlResults = "pf+, pan+, brac+, pctrl-, nctrl-";
        } else if (data.contains("Negative")) {
            mlResults = "pf-, pan-, brac+, pctrl-, nctrl-";
        } else if (data.contains("Invalid")) {
            mlResults = "Invalid";
        }

        resultLabel = mlResults;
    }
}
