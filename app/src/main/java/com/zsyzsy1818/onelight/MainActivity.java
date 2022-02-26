package com.zsyzsy1818.onelight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by  小可爱兔宝贝 on 2022/2/24
 * Mail:   zhaoshiyu900310@163.com
 * Description:
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlashLightUtils.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);


        imageButton.setImageResource(R.drawable.light_on);
        FlashLightUtils.openFlash();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                //关
//                FlashLightUtils.init();
                if (FlashLightUtils.getFlashLightStatus()) {
                    imageButton.setImageResource(R.drawable.light_off);
                    FlashLightUtils.closeFlash();
                    Toast.makeText(this, "手电筒已关闭", Toast.LENGTH_SHORT).show();
                }
                //开
                else {
                    imageButton.setImageResource(R.drawable.light_on);
                    FlashLightUtils.openFlash();
                    Toast.makeText(this, "手电筒已打开", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //todo 增加一个switch按钮，用于选择当app返回桌面时是否关闭手电筒,如果开启以下代码，app退出将关闭手电筒（貌似有点问题）
//        FlashLightUtils.closeFlash();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FlashLightUtils.flashLightDisabled();
    }
}