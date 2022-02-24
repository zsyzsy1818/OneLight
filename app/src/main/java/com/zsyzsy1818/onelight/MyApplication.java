package com.zsyzsy1818.onelight;

import android.app.Application;
import android.content.Context;
/**
 * Created by  小可爱兔宝贝 on 2022/2/24
 * Mail:   zhaoshiyu900310@163.com
 * Description:
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }
}