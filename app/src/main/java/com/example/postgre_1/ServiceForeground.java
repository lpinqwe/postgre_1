package com.example.postgre_1;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class ServiceForeground extends Service {
    Context context =this;
    SensorManagerClass mSensorManager = new SensorManagerClass(this);
    Handler handler = new Handler();
    NetworkConnection network = new NetworkConnection();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service Started", "service Started");
        mSensorManager.addData(("started"));

        mSensorManager.onCreateSensors();
        mSensorManager.activateSensors();
        handler.postDelayed(runnable, 10000); // Запускаем через 10 секунд

        return START_STICKY;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.i("SERVICE_SEND_DATA","dataSended");
                network.sndFunc(mSensorManager.getDATA(), context);//send
                mSensorManager.clearData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        handler.postDelayed(this, 10*1000); // Повторно запускаем через 5 min
        }
    };

    @Override
    public void onDestroy() {
        try {
            mSensorManager.sensorRegister();
            mSensorManager.addData(("destroyed"));
            network.sndFunc(mSensorManager.getDATA(), this);//send

            mSensorManager.clearData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.i("service DISABLED", "service DISABLED");
        Log.i("DataCleared", "DataCleared");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
