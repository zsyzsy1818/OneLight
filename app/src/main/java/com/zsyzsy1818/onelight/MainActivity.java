package com.zsyzsy1818.onelight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * Created by  小可爱兔宝贝 on 2022/2/24
 * Mail:   zhaoshiyu900310@163.com
 * Description:
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton;
    private boolean flash;

    @RequiresApi(api = Build.VERSION_CODES.M) public void requestIgnoreBatteryOptimizations () {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestIgnoreBatteryOptimizations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        imageButton.setImageResource(R.drawable.light_off);
        flash = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                //关
                if (!flash) {
                    imageButton.setImageResource(R.drawable.light_off);

                    FlashLightUtils.getInstance().flashLightChange(MyApplication.context);
                    flash = true;
                    Toast.makeText(this, "大可爱鹿宝贝", Toast.LENGTH_SHORT).show();
                }
                //开
                else {
                    imageButton.setImageResource(R.drawable.light_on);
                    FlashLightUtils.getInstance().flashLightChange(MyApplication.context);
                    flash = false;
                    Toast.makeText(this, "小可爱兔宝贝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        //todo 增加一个switch按钮，用于选择当app返回桌面时是否关闭手电筒
//        closeFlash();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (manager != null && torchCallback != null) {
//            manager.unregisterTorchCallback(torchCallback);
//        }
    }
}