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

    private ImageButton imageButton;
    private boolean flash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlashLightUtils.flashLightStatus(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);




        Log.e("TAG", " "+ Val.FlashLightStatus);
        imageButton.setImageResource(R.drawable.light_on);
        FlashLightUtils.openFlash();
        Log.e("TAG", " "+ Val.FlashLightStatus);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                //关
                if (Val.FlashLightStatus) {
                    imageButton.setImageResource(R.drawable.light_off);

                    FlashLightUtils.closeFlash();
                    Toast.makeText(this, "手电筒已关闭", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", " "+ Val.FlashLightStatus);
                }
                //开
                else {
                    imageButton.setImageResource(R.drawable.light_on);
                    FlashLightUtils.openFlash();
                    Toast.makeText(this, "手电筒已打开", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", " "+ Val.FlashLightStatus);
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
        FlashLightUtils.flashLightDisabled();
        super.onDestroy();
    }
}