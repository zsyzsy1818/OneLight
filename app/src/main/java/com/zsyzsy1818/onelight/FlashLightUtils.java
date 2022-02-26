package com.zsyzsy1818.onelight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by  小可爱兔宝贝 on 2022/2/24
 * Mail:   zhaoshiyu900310@163.com
 * Description:     手电筒工具类  FlashLightUtils.getInstance().flashLightChange(context);
 */
public class FlashLightUtils {
    private static final String TAG = FlashLightUtils.class.getSimpleName();
    private static CameraManager manager;
    private static android.hardware.Camera camera;
//    public static String FlashLightStatus = "";

    @SuppressLint("StaticFieldLeak")
    private FlashLightUtils() {
    }

    public static FlashLightUtils getInstance() {
        //第一次调用getInstance方法时才加载SingletonHolder并初始化sInstance


        return SingletonHolder.sInstance;
    }

    //静态内部类
    private static class SingletonHolder {
        private static final FlashLightUtils sInstance = new FlashLightUtils();
    }

    //判断手电筒状态
    private static CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(String cameraId, boolean enabled) {
            super.onTorchModeChanged(cameraId, enabled);
            manager.unregisterTorchCallback(torchCallback);
            Val.FlashLightStatus=enabled;
            Log.e(TAG, "手电状态为: " + Val.FlashLightStatus);
        }
    };

    public static void flashLightStatus(Context context) {
        if (manager == null) {
            manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        }
        manager.registerTorchCallback(torchCallback, null);


    }

    public static void flashLightDisabled() {
        if (manager != null && torchCallback != null) {
            manager.unregisterTorchCallback(torchCallback);
        }


    }

    public static void openFlash() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (manager != null) {
                    manager.setTorchMode("0", true);
                    Val.FlashLightStatus = true;
                }
            } else {
                camera = android.hardware.Camera.open();
                android.hardware.Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                Val.FlashLightStatus = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeFlash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (manager == null) {
                    return;
                }
                manager.setTorchMode("0", false);
                Val.FlashLightStatus = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (camera == null) {
                return;
            }
            camera.stopPreview();
            camera.release();
            Val.FlashLightStatus = false;
        }
    }

}
