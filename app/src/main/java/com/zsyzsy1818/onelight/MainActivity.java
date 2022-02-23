package com.zsyzsy1818.onelight;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton;
    private boolean flash;


    private CameraManager manager;
    private android.hardware.Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manager == null) {
            manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        }
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        imageButton.setImageResource(R.drawable.light_on);
        openFlash();
        flash=true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                //关
                if (flash) {
                    imageButton.setImageResource(R.drawable.light_off);
                    closeFlash();
                    flash = false;
                    Toast.makeText(this, "大可爱鹿宝贝", Toast.LENGTH_SHORT).show();
                }
                //开
                else {
                    imageButton.setImageResource(R.drawable.light_on);
                    openFlash();
                    flash = true;
                    Toast.makeText(this, "小可爱兔宝贝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        if (b==true){
//            openFlash();
////            compoundButton.setButtonDrawable(R.drawable.light_on);
//            compoundButton.setButtonDrawable(R.drawable.light_on);
//            Toast.makeText(this, "大可爱鹿宝贝", Toast.LENGTH_SHORT).show();
//        } else if (b == false) {
//            closeFlash();
////            compoundButton.setButtonDrawable(R.drawable.light_off);
//            compoundButton.setButtonDrawable(R.drawable.light_off);
//            Toast.makeText(this, "小可爱兔宝贝", Toast.LENGTH_SHORT).show();
//        }
//
//    }


    private void openFlash() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (manager != null) {
                    manager.setTorchMode("0", true);
                }
            } else {
//                camera = android.hardware.Camera.open();
//                android.hardware.Camera parameters=camera.getParameters();
//                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                camera.setParameters(parameters);
//                camera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeFlash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (manager == null) {
                    return;
                }
                manager.setTorchMode("0", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (camera == null) {
                return;
            }
            camera.stopPreview();
            camera.release();
        }
    }

    private CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(String cameraId, boolean enabled) {
            super.onTorchModeChanged(cameraId, enabled);
            manager.unregisterTorchCallback(torchCallback);
            if (!enabled) {
                //手电筒状态关闭，执行开启
                openFlash();
            } else {
                //手电筒状态开启，执行关闭
                closeFlash();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null && torchCallback != null) {
            manager.unregisterTorchCallback(torchCallback);
        }
    }
}