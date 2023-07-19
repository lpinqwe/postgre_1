package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class StringSQL extends Activity implements SensorEventListener {
    String phoneData;
    public void phoneDataBuild(){

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER_UNCALIBRATED){
    phoneData=String.valueOf(sensorEvent.values[1]);
       }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
