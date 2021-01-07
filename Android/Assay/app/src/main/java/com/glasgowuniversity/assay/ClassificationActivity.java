package com.glasgowuniversity.assay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.glasgowuniversity.assay.configs.ModelConfig;
import com.glasgowuniversity.assay.configs.TfliteModel;
import com.otaliastudios.cameraview.CameraView;

import java.io.IOException;
import java.util.List;

public class ClassificationActivity extends AppCompatActivity
        implements ClassificationFrameProcessor.ClassificationListener {

    private CameraView cameraView;
    private TextView classificationResult;
    private ClassificationFrameProcessor classificationFrameProcessor;
    private ImageView imageView;
    private Button reset;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);

        classificationResult = findViewById(R.id.classification_results);
        cameraView = findViewById(R.id.classification_cameraView);
        cameraView.setLifecycleOwner(this);
        imageView = findViewById(R.id.classification_imageView);

        String path = ClassificationFrameProcessor.getImageUri();
        Bitmap imgFile = BitmapFactory.decodeFile(path);

        imageView.setImageBitmap(imgFile);

        initClassification();
        reset = findViewById(R.id.classification_reset_button);
        save = findViewById(R.id.classification_save_button);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassificationActivity.this, CustomCamActivity.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientInfoActivity.setResults(classificationResult.getText().toString());
                startActivity(new Intent(ClassificationActivity.this, PatientInfoActivity.class));
            }
        });
    }

    private void initClassification() {
        try {
            ModelConfig modelConfig = new TfliteModel();
            classificationFrameProcessor = new ClassificationFrameProcessor(this, this, modelConfig);
            cameraView.addFrameProcessor(classificationFrameProcessor);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Frame Processor initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClassifiedFrame(List<ClassificationResult> classificationResults) {
        StringBuilder results = new StringBuilder();
        if (classificationResults.size() == 0) {
            results.append("No results");
        } else {
            int i = 0;
            for (ClassificationResult classificationResult : classificationResults) {
                results.append(classificationResult.title)
                        .append("(")
                        .append(classificationResult.confidence * 100)
                        .append("%)\n");
                i++;
            }
        }

        runOnUiThread(() -> classificationResult.setText(results));

    }
}