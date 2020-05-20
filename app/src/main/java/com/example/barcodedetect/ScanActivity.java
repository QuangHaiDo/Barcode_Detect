package com.example.barcodedetect;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

import common.BarcodeScanningProcessor;
import common.CameraSource;
import common.CameraSourcePreview;
import common.GraphicOverlay;

public class ScanActivity extends Activity {

    private static final String TAG = "ScanActivity";
    private ImageView flashButton;
    private ImageView closeButton;
    private CameraSource cameraSource;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private TextView textResult;
    private Button clearText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        textResult = findViewById(R.id.text_result);
        textResult.setMovementMethod(new ScrollingMovementMethod());
        preview = findViewById(R.id.camera_preview);

        flashButton = findViewById(R.id.flash_button);
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashButton.isSelected()) {
                    flashButton.setSelected(false);
                    cameraSource.updateFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                } else {
                    flashButton.setSelected(true);
                    cameraSource.updateFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
            }
        });

        closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textResult.getText().toString().length()!=0){
                    Intent i = new Intent(getApplicationContext(),ProductResultActivity.class);
                    i.putExtra("textResult",textResult.getText().toString());
                    startActivity(i);
                }
            }
        });


        clearText = findViewById(R.id.clear_textview);
        clearText.setOnClickListener(v -> textResult.setText(""));

        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = findViewById(R.id.camera_preview_graphic_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        createCameraSource();
        startCameraSource();
    }


    private void createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }
        try {
            BarcodeScanningProcessor barcodeScanningProcessor = new BarcodeScanningProcessor(textResult);
            cameraSource.setMachineLearningFrameProcessor(barcodeScanningProcessor);
        } catch (Exception e) {
            Log.e(TAG, "Can not create image processor: " + e);
            Toast.makeText(
                    getApplicationContext(),
                    "Can not create image processor: " + e.getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }


    @Override
    public void onResume() {
        textResult.setText("");
        super.onResume();
        Log.d(TAG, "onResume");
        startCameraSource();
    }
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }
}
