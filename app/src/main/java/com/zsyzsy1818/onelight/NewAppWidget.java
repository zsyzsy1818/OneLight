package com.zsyzsy1818.onelight;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
/**
 * Created by  小可爱兔宝贝 on 2022/2/24
 * Mail:   zhaoshiyu900310@163.com
 * Description:
 */
public class NewAppWidget extends AppWidgetProvider {
    static int num = 0;
    static String tag_action = "Widget.Button.Click";

   static RemoteViews views;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Intent intent = new Intent(context, MyService.class);
        context.startService(intent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        FlashLightUtils.getInstance().flashLightDisabled();
    }

    public static class MyService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            ComponentName thisWidget = new ComponentName(this, NewAppWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);

            //点击按钮时
            if (intent.getAction() != null) {
                if (intent.getAction().equals(tag_action)) {
                    FlashLightUtils.getInstance().flashLightChange(MyApplication.context);

                    remoteViews.setImageViewResource(R.id.imageButton,R.drawable.dakeai);
                    manager.updateAppWidget(thisWidget, remoteViews);
                }
            }
            //定义一个Intent来发送按钮Action
            Intent intent1 = new Intent();
            intent1.setAction(tag_action);

            // 用Intent实例化一个PendingIntent
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
            manager.updateAppWidget(thisWidget, remoteViews);
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

}