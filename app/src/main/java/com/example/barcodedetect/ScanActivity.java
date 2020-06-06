package com.example.barcodedetect;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
    private Button copyClipBoard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        textResult = findViewById(R.id.text_result);
        textResult.setMovementMethod(new ScrollingMovementMethod());
        preview = findViewById(R.id.camera_preview);
        textResult.setPaintFlags(textResult.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
                String code = textResult.getText().toString();
                if (isValid(code)){
                    Intent i = new Intent(getApplicationContext(),ProductResultActivity.class);
                    i.putExtra("textResult",textResult.getText().toString());
                    startActivity(i);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.codeInvalidToast,
                            Toast.LENGTH_SHORT
                    ) .show();
                }
            }
        });


        clearText = findViewById(R.id.clear_textview);
        clearText.setOnClickListener(v -> textResult.setText(""));

        copyClipBoard = findViewById(R.id.copy_to_clipboard);
        copyClipBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = textResult.getText().toString();
                if (code.length()>0){
                    ClipboardManager clipboard = (ClipboardManager)
                            getSystemService(Context.CLIPBOARD_SERVICE);
                    // Creates a new text clip to put on the clipboard
                    ClipData clip = ClipData.newPlainText("code_detected",textResult.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(),"Đã sao chép mã: \n"+textResult.getText().toString(),Toast.LENGTH_SHORT).show();

                }
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

    public boolean isValid(String s){
        if (s.matches("([a-zA-Z0-9]){8,20}")) return true;
        else return false;
    }

    @Override
    public void onResume() {
        textResult.setText("");
        super.onResume();
        Log.d(TAG, "onResume");
        flashButton.setSelected(false);
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
