package com.zszdevelop.planman.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
    public class ShakeListenerUtils implements SensorEventListener {

        Context mContext;
        private SensorManager sensorManager;
        private Sensor sensor;
        private onShakeListener onShakeListener;
        private float lastX;
        private float lastY;
        private float lastZ;
        private static final int SPEED_SHRESHOLD = 3000;
        private long lastUpdateTime;
        private static final int UPTATE_INTERVAL_TIME = 70;
        public ShakeListenerUtils(Context context){
            mContext = context;
            start();
        }
        private void start() {
            sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
            if(sensorManager != null)
            {
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
            if(sensor != null)
            {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }

        private void stop(){
            sensorManager.unregisterListener(this);
        }
        @Override
        //当传感器精度改变时回调该方法
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        // 重力感应器感应获得变化数据
        public void onSensorChanged(SensorEvent event) {
            long currentUpdateTime = System.currentTimeMillis();
            long timeInterval = currentUpdateTime - lastUpdateTime;
            if (timeInterval < UPTATE_INTERVAL_TIME)
                return;
            lastUpdateTime = currentUpdateTime;
            // 获得x,y,z坐标
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // 获得x,y,z的变化值
            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;

            // 将现在的坐标变成last坐标
            lastX = x;
            lastY = y;
            lastZ = z;

            // 计算出速度
            double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                    * deltaZ)
                    / timeInterval * 10000;
            Log.e("thelog", speed + "===========log===================");
            int i = 0;
            if (speed >= SPEED_SHRESHOLD) { //速度大于 阀值 开始监听摇动   // 达到速度阀值，发出提示
                onShakeListener.onShake(i++);
            }
        }


         // 设置重力感应监听器
        public void setOnShakeListener(onShakeListener listener) {
            onShakeListener = listener;
        }

         // 摇晃监听接口
        public interface onShakeListener{
            void onShake(int i);
        }
    }