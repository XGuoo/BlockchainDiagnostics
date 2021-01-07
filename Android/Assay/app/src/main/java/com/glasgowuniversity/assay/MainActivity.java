package com.glasgowuniversity.assay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dev_create = findViewById(R.id.main_button_create_device);
        Button dev_info = findViewById(R.id.main_button_dev_info);
        Button start = findViewById(R.id.main_button_start);
        Button results = findViewById(R.id.main_button_results);
        Button logout = findViewById(R.id.main_button_logout);

        Button photoButton = findViewById(R.id.photodatabutton);
        photoButton.setOnClickListener(this);
        dev_create.setOnClickListener(this);
        dev_info.setOnClickListener(this);
        start.setOnClickListener(this);
        results.setOnClickListener(this);
        logout.setOnClickListener(this);

        if(LoginActivity.isAuthenticated()){
            switch (LoginActivity.getStatus()) {
                case "Admin":
                    dev_create.setVisibility(View.VISIBLE);
                    dev_info.setVisibility(View.VISIBLE);
                    start.setVisibility(View.VISIBLE);
                    results.setVisibility(View.VISIBLE);
                    break;
                case "Operator":
                    dev_create.setVisibility(View.GONE);
                    dev_info.setVisibility(View.VISIBLE);
                    start.setVisibility(View.VISIBLE);
                    results.setVisibility(View.VISIBLE);
                    break;
                case "Manufacturer":
                    dev_create.setVisibility(View.VISIBLE);
                    dev_info.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                    results.setVisibility(View.GONE);
                    break;
                case "Analyst":
                    dev_create.setVisibility(View.GONE);
                    dev_info.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                    results.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i){
            case R.id.main_button_create_device:
                if(LoginActivity.getStatus().equals("Manufacturer") || LoginActivity.getStatus().equals("Admin"))
                    startActivity(new Intent(this, DevInfoManufacturerActivity.class));
                break;

            case R.id.main_button_dev_info:
                if(LoginActivity.getStatus().equals("Operator") || LoginActivity.getStatus().equals("Admin"))
                    startActivity(new Intent(this, DevInfoOperatorActivity.class));
                break;

            case R.id.main_button_start:
                if(LoginActivity.getStatus().equals("Operator")  || LoginActivity.getStatus().equals("Admin"))
                    startActivity(new Intent(this, BlunoUIActivity.class));
                break;

            case R.id.main_button_results:
                if(!LoginActivity.getStatus().equals("Manufacturer"))
                    startActivity(new Intent(this, DevInfoAnalystActivity.class));
                break;
            case R.id.main_button_logout:
                logout();
                break;
            case R.id.photodatabutton:
                startActivity(new Intent(this, PatientInfoActivity.class));
                break;
        }

    }

    private void logout() {
        switch (LoginActivity.getStatus()){
            case "Operator":
                Operator.reset();
                break;
            case "Manufacturer":
                Manufacturer.reset();
                break;
            case "Analyst":
                Analyst.reset();
                break;
            case "Admin":
                Admin.reset();
                break;
        }
        LoginActivity.setAuthenticated(false);
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAndRequestPermissions();
        if(!LoginActivity.isAuthenticated()) startActivity(new Intent(this, LoginActivity.class));

    }

    private  void checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int location2Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int bluetoothPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (location2Permission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (bluetoothPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]),1);
        }
    }

    @Override
    public void onBackPressed() {}
}
