package com.glasgowuniversity.assay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.format;

public class DevInfoAnalystActivity extends AppCompatActivity {

    private static Context context;
    private TextView analystId;
    private TextView analystUsername;
    private static TextView deviceId;
    private static TextView testName;
    private static TextView manufacturerId;
    private static TextView dateOfManufacture;
    private static TextView expireDate;
    private static TextView benchNumber;
    private static TextView productionPlace;
    private static TextView status;
    private static TextView operatorId;
    private static TextView testDate;
    private static TextView patientId;
    private static TextView dateOfBirth;
    private static TextView gender;
    private static TextView weight;
    private static TextView imageUri;
    private static TextView result;
    private static TextView testPlace;
    Button scan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info_analyst);

        analystId = findViewById(R.id.dev_info_analyst_textView_analyst_id);
        analystUsername = findViewById(R.id.dev_info_analyst_textView_username);
        deviceId = findViewById(R.id.dev_info_analyst_textView_dev_id);
        testName = findViewById(R.id.dev_info_analyst_textView_test_name);
        manufacturerId = findViewById(R.id.dev_info_analyst_textView_manufacturer_id);
        dateOfManufacture = findViewById(R.id.dev_info_analyst_textView_manufacturing_date);
        expireDate = findViewById(R.id.dev_info_analyst_textView_expiration_date);
        benchNumber = findViewById(R.id.dev_info_analyst_textView_bench_number);
        productionPlace = findViewById(R.id.dev_info_analyst_textView_producing_area);
        status = findViewById(R.id.dev_info_analyst_textView_dev_state);
        operatorId = findViewById(R.id.dev_info_analyst_textView_operator_id);
        testDate = findViewById(R.id.dev_info_analyst_textView_test_date);
        patientId = findViewById(R.id.dev_info_analyst_textView_patient_id);
        dateOfBirth = findViewById(R.id.dev_info_analyst_textView_date_of_birth);
        gender = findViewById(R.id.dev_info_analyst_textView_gender);
        weight = findViewById(R.id.dev_info_analyst_textView_weight);
        imageUri = findViewById(R.id.dev_info_analyst_textView_img_url);
        result = findViewById(R.id.dev_info_analyst_textView_result);
        testPlace = findViewById(R.id.dev_info_analyst_textView_location);

        scan = findViewById(R.id.dev_info_analyst_button_scan_qr);

        if(LoginActivity.getStatus().equals("Operator")){

            analystId.setText(Html.fromHtml(format("<b>%s:</b>   %s (%s)", getString(R.string.label_user_id),
                    Operator.getOperatorID(), LoginActivity.getStatus())));
            analystUsername.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_username),
                    Operator.getOperatorUsername())));
        } else {

            analystId.setText(Html.fromHtml(format("<b>%s:</b>   %s (%s)", getString(R.string.label_user_id),
                    Analyst.getAnalystID(), LoginActivity.getStatus())));
            analystUsername.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_username),
                    Analyst.getAnalystUsername())));
        }

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssayDevice.setDeviceTest(false);
                AssayDevice.setDevicePost(false);
                startActivity(new Intent(DevInfoAnalystActivity.this, QrScannerActivity.class));
            }
        });

        testPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testPlace.getText().toString().contains("Latitude")){
                    String[] temp = testPlace.getText().toString().substring(15).split(",");
                    double lat = Double.valueOf(temp[0].substring(10));
                    double lng = Double.valueOf(temp[1].substring(12));
                    MapsActivity.setTestLocation(lat, lng);
                    startActivity(new Intent(DevInfoAnalystActivity.this, MapsActivity.class));
                }
            }
        });


        context = getApplicationContext();
    }

    public static void update() {
        deviceId.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_dev_id),AssayDevice.getDeviceId())));
        testName.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_test_name), AssayDevice.getTestName())));
        manufacturerId.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_manufacturer_id), AssayDevice.getManufacture())));
        dateOfManufacture.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_manufacturing_date), AssayDevice.getDateOfManufacture())));
        expireDate.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_expiration_date), AssayDevice.getExpireDate())));
        benchNumber.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_bench_number), AssayDevice.getBenchNumber())));
        productionPlace.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_producing_area), AssayDevice.getProductionPlace())));
        status.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_status), AssayDevice.getStatus())));
        operatorId.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_operator_id), AssayDevice.getOperator())));
        testDate.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_test_date), AssayDevice.getTestDate())));
        patientId.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_patient_id), AssayDevice.getPatientId())));
        dateOfBirth.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_date_of_birth), AssayDevice.getDateOfBirth())));
        gender.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_gender), AssayDevice.getGender())));
        weight.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_weight), AssayDevice.getWeight())));
        imageUri.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_img_url), AssayDevice.getUrl())));
        result.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_results), AssayDevice.getResult())));
        testPlace.setText(Html.fromHtml(format("<b>%s:</b>   %s", context.getString(R.string.label_location), AssayDevice.getTestPlace())));
    }
}
