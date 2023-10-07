package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private long lastUpdate;
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("SENSOR","POINT TWO ###########");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.i("SENSOR","POINT 3 ###########");
            getAccelerometer(event);
            Log.i("SENSOR","POINT 4##");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
Log.i("SENSOR","accuracyChanged");
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        Log.println(Log.DEBUG,"SENSOR","ACC "+x);

        Log.i("SENSORS","ACCELEROMETER"+x+" "+y+" "+z);
    }
}
