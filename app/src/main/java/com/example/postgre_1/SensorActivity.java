package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private long lastUpdate;
    List<Integer> sensorsList;


    public void importSensorList(List<Integer> list) {
        sensorsList = list;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        //Log.i("SENSOR", "POINT TWO ###########");

   }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Log.i("SENSOR", "accuracyChanged");
    }



}
