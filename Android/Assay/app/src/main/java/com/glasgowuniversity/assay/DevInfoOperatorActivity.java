package com.glasgowuniversity.assay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.format;

public class DevInfoOperatorActivity extends AppCompatActivity {

    private TextView operatorId;
    private TextView deviceId;
    private TextView operatorUsername;
    private Button scan;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info_operator);

        operatorId = findViewById(R.id.dev_info_operator_textView_operator_id);
        deviceId = findViewById(R.id.dev_info_operator_textView_dev_id);
        operatorUsername = findViewById(R.id.dev_info_operator_textView_username);
        scan = findViewById(R.id.dev_info_operator_button_scan_qr);
        next = findViewById(R.id.dev_info_operator_button_next);

        operatorId.setText(Html.fromHtml(format("<b>%s:</b>   %s (%s)", getString(R.string.label_user_id),
                Operator.getOperatorID(), LoginActivity.getStatus())));
        operatorUsername.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_username),Operator.getOperatorUsername())));

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssayDevice.setDeviceTest(false);
                AssayDevice.setDevicePost(true);
                startActivity(new Intent(DevInfoOperatorActivity.this, QrScannerActivity.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevInfoOperatorActivity.this, CustomCamActivity.class));
            }
        });

        if(AssayDevice.getDeviceId()==null || AssayDevice.getDeviceId().isEmpty()) next.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!AssayDevice.getDeviceId().isEmpty()) {
            deviceId.setText(Html.fromHtml(format("<b>%s:</b>   %s", getString(R.string.label_dev_id),AssayDevice.getDeviceId())));
            next.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        AssayDevice.resetDevice();
        startActivity(new Intent(this, MainActivity.class));
    }
}
