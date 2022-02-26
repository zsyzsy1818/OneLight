package com.zsyzsy1818.onelight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

/**
 * Created by  小可爱兔宝贝 on 2022/2/24
 * Mail:   zhaoshiyu900310@163.com
 * Description:     手电筒工具类  初始化：FlashLightUtils.init();
 */
public class FlashLightUtils {
    private static final String TAG = FlashLightUtils.class.getSimpleName();
    private static CameraManager manager;
    private static android.hardware.Camera camera;
    private static boolean FlashLightStatus;

    @SuppressLint("StaticFieldLeak")
    private FlashLightUtils() {
    }

    public static FlashLightUtils init() {
        //第一次调用getInstance方法时才加载SingletonHolder并初始化sInstance
        if (manager == null) {
            manager = (CameraManager) MyApplication.context.getSystemService(Context.CAMERA_SERVICE);
        }
        manager.registerTorchCallback(torchCallback, null);

        return SingletonHolder.sInstance;
    }

    //静态内部类
    private static class SingletonHolder {
        private static final FlashLightUtils sInstance = new FlashLightUtils();
    }


    private static final CameraManager.TorchCallback torchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(String cameraId, boolean enabled) {
            super.onTorchModeChanged(cameraId, enabled);
            manager.unregisterTorchCallback(torchCallback);
            setFlashLightStatus(enabled);
            Log.e(TAG, "手电状态为: " + getFlashLightStatus());
        }
    };


    /**
     * 解绑 放到onDestroy();
     */
    public static void flashLightDisabled() {
        if (manager != null && torchCallback != null) {
            manager.unregisterTorchCallback(torchCallback);
        }


    }

    /**
     * 开启闪光灯
     */
    public static void openFlash() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (manager != null) {
                    manager.setTorchMode("0", true);
                    setFlashLightStatus(true);
                    init();
                }
            } else {
                camera = android.hardware.Camera.open();
                android.hardware.Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                setFlashLightStatus(true);
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭闪光灯
     */
    public static void closeFlash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (manager == null) {
                    return;
                }
                manager.setTorchMode("0", false);
                setFlashLightStatus(false);
                init();
//                manager=null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (camera == null) {
                return;
            }
            camera.stopPreview();
            camera.release();
            setFlashLightStatus(false);
            init();
//            camera=null;
        }
    }

    /**
     * 获取手电筒状态
     *
     * @return 手电筒状态 true表示开启   false表示关闭
     */
    public static boolean getFlashLightStatus() {
        return FlashLightStatus;
    }

    private static void setFlashLightStatus(boolean flashLightStatus) {
        FlashLightStatus = flashLightStatus;
    }
}
