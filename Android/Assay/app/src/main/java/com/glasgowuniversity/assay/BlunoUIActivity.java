package com.glasgowuniversity.assay;


import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlunoUIActivity extends BlunoLibrary {
    private Button buttonScan;
    private Button buttonSerialSend;
    private Button buttonNext;
    private Button buttonUpload;
    private EditText serialSendText;
    private EditText serialhour;
    private EditText serialSendminute;
    private EditText serialsecond;
    private TextView serialReceivedText;
    private TextView devId;
    private String stringHolder = "";
    private String deviceId;
    private int justStarted;
    private static boolean isConnected;
    private static boolean isFinished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluno_ui);
        onCreateProcess();

        serialBegin(115200);                                                    //set the Uart Baudrate on BLE chip to 115200
        isFinished = false;
        justStarted = 0;

        serialReceivedText = findViewById(R.id.bluno_textView_data_received);    //initial the EditText of the received data
        serialSendText = findViewById(R.id.bluno_input_temperature);            //initial the EditText of the sending data
        serialhour = findViewById(R.id.bluno_input_hour);
        serialSendminute = findViewById(R.id.bluno_input_minutes);
        serialsecond = findViewById(R.id.bluno_input_seconds);
        buttonSerialSend = findViewById(R.id.bluno_button_send);        //initial the button for sending the data
        buttonNext = findViewById(R.id.bluno_button_next);
        buttonUpload = findViewById(R.id.bluno_button_upload);

        buttonUpload.setVisibility(View.GONE);
        buttonNext.setVisibility(View.GONE);

        devId = findViewById(R.id.deviceId);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = readFromFile();
                writeToDatabase(data);
                Toast.makeText(getApplicationContext(),"UPLOADING . . . ", Toast.LENGTH_LONG).show();
                buttonUpload.setVisibility(View.GONE);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportToFile();
                startActivity(new Intent(BlunoUIActivity.this, CustomCamActivity.class));
            }
        });

        buttonSerialSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                List data = new ArrayList<String>();
                data.add(serialSendText.getText().toString());
                data.add(serialhour.getText().toString());
                data.add(serialSendminute.getText().toString());
                data.add(serialsecond.getText().toString());
                data.add(0);
                String abc;
                abc = TextUtils.join(",", data);
                isFinished = false;
                justStarted = 1;
                buttonUpload.setVisibility(View.GONE);
                buttonNext.setVisibility(View.GONE);
                serialSend(abc);                //send the data to the BLUNO
            }
        });

        buttonScan = (Button) findViewById(R.id.bluno_button_scan);                    //initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!isConnected){
                    AssayDevice.setDeviceTest(true);
                    AssayDevice.setDevicePost(true);
                    startActivity(new Intent(BlunoUIActivity.this, QrScannerActivity.class));
                }
                if(isConnected)buttonScanOnClickProcess();
            }
        });
    }

    protected void onResume() {
        onResumeProcess();//onResume Process by BlunoLibrary
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        if(!AssayDevice.getDeviceId().isEmpty()){
            if(AssayDevice.isDeviceTest()) buttonScanOnClickProcess();//Alert Dialog for selecting the BLE device
            deviceId = AssayDevice.getDeviceId();
            devId.setText(deviceId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResultProcess(requestCode, resultCode, data);                    //onActivityResult Process by BlunoLibrary
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();                                                        //onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();                                                        //onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();                                                        //onDestroy Process by BlunoLibrary
    }

    @Override
    public void onConnectionStateChange(connectionStateEnum theConnectionStateEnum) {//Once connection state changes, this function will be called
        switch (theConnectionStateEnum) {                                            //Four connection state
            case isConnected:
                buttonScan.setText("Connected");
                break;
            case isConnecting:
                buttonScan.setText("Connecting");
                break;
            case isToScan:
                buttonScan.setText("Scan");
                isConnected = false;
                justStarted = 0;
                break;
            case isScanning:
                buttonScan.setText("Scanning");
                break;
            case isDisconnecting:
                buttonScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        isConnected = true;
        if(!isFinished){
            String checker;
            if(stringHolder.contains(":") && stringHolder.contains(".")){
                stringHolder += theString;
                checker = stringHolder.replaceAll("\\{","")
                        .replaceAll("\\}","");
                serialReceivedText.setText(checker);
                stringHolder = "[" + stringHolder + "]";
                writeToFile(stringHolder, this);
                stringHolder = "";
                if(checker.contains("0 : 0 : 0")){
                    isFinished = true;
                    if(justStarted == 1){
                        buttonUpload.setVisibility(View.VISIBLE);
                        buttonNext.setVisibility(View.VISIBLE);
                    }
                }
            }
            if(theString.contains(":") || theString.contains("."))stringHolder += theString;
        }
    }

    private void writeToDatabase(String data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String fileName = String.format("assay_%s_%s", AssayDevice.getDeviceId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        DatabaseReference myRef = database.getReference("temperatures/"+fileName);
        myRef.setValue(data);
    }
}

