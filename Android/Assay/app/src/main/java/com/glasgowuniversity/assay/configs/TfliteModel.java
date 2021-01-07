package com.glasgowuniversity.assay.configs;

public class TfliteModel extends ModelConfig {

    @Override
    public String getModelFilename() {
        return "result_analysis_model.tflite";
    }

    @Override
    public String getLabelsFilename() {
        return "labels.txt";
    }

    @Override
    public int getInputWidth() {
        return 128;
    }

    @Override
    public int getInputHeight() {
        return 128;
    }

    @Override
    public int getInputSize() {
        return getInputWidth() * getInputHeight() * getChannelsCount() * FLOAT_BYTES_COUNT;
    }

    @Override
    public int getChannelsCount() {
        return 3;
    }

    @Override
    public float getStd() {
        return 255.f;
    }

    @Override
    public float getMean() {
        return 255.f;
    }

    @Override
    public boolean isQuantized() {
        return false;
    }
}
