package com.zsyzsy1818.onelight;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

/**
 * Created by  小可爱兔宝贝 on 2022/2/25
 * Mail:   zhaoshiyu900310@163.com
 * Description:
 */
public class FlashLightService extends Service {
    static int num = 0;
    static String tag_action = "Widget.Button.Click";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ComponentName thisWidget = new ComponentName(this, NewAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);

        //点击按钮时
        if (intent.getAction() != null) {
            if (intent.getAction().equals(tag_action)) {
                FlashLightUtils.getInstance().flashLightStatus(MyApplication.context);
                Log.d("TAG", "点击了appwidget: imageButton");
                if (num % 2 == 1) {
                    remoteViews.setImageViewResource(R.id.widget_button,R.drawable.xiaokeai);
                }else
                    remoteViews.setImageViewResource(R.id.widget_button,R.drawable.dakeai);
                num++;
            }
        }
        //定义一个Intent来发送按钮Action
        Intent intent1 = new Intent();
        intent1.setAction(tag_action);

        // 用Intent实例化一个PendingIntent
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
        manager.updateAppWidget(thisWidget, remoteViews);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        num=0;
        Log.d("TAG", "MyService被销毁了: ");
        Intent service = new Intent(this, FlashLightService.class);
        this.startService(service);
        super.onDestroy();
    }
}
