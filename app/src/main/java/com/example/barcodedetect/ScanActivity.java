package com.example.barcodedetect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private CameraSource cameraSource;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private TextView textResult;
    private Button startProductPreview;
    private Button clearText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        textResult = findViewById(R.id.text_result);
        textResult.setMovementMethod(new ScrollingMovementMethod());
        preview = findViewById(R.id.camera_preview);
        startProductPreview = findViewById(R.id.btn_search_barcode);

        /**
         * Pass barcode here to start Product_preview with result
        * */
        startProductPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProductResultActivity.class));
            }
        });
        clearText = findViewById(R.id.clear_textview);
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textResult.setText("");
            }
        });
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
