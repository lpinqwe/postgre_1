package com.example.postgre_1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
/*
 * sensorsClass to read:
 * 1  TYPE_GAME_ROTATION_VECTOR
 * 2  TYPE_GYROSCOPE
 * 3  TYPE_GRAVITY
 * 4  TYPE_ORIENTATION
 * 5  TYPE_PROXIMITY
 * 6  TYPE_ACCELEROMETER
 * 7  TYPE_ROTATION_VECTOR
 * 8  TYPE_LINEAR_ACCELERATION
 * 9  TYPE_AMBIENT_TEMPERATURE
 * 10 TYPE_LIGHT
 */
public class SensorManagerClass implements SensorEventListener {
    String SENSOR_DATA = "";
    private android.hardware.SensorManager mSensorManager;
    List<Integer> sensorArrayList = new ArrayList<>();
    FileManager1 fileManager1= new FileManager1();
    Context context_1;
     SensorManagerClass(Context context){
        this.context_1 = context;
    }
public void onCreateSensors(){

    mSensorManager = (android.hardware.SensorManager) context_1.getSystemService(SENSOR_SERVICE);

    sensorArrayList.add(Sensor.TYPE_GAME_ROTATION_VECTOR);
    sensorArrayList.add(Sensor.TYPE_GYROSCOPE);
    sensorArrayList.add(Sensor.TYPE_GRAVITY);
    sensorArrayList.add(Sensor.TYPE_PROXIMITY);
    sensorArrayList.add(Sensor.TYPE_ACCELEROMETER);
    sensorArrayList.add(Sensor.TYPE_ROTATION_VECTOR);
    sensorArrayList.add(Sensor.TYPE_LINEAR_ACCELERATION);
    sensorArrayList.add(Sensor.TYPE_AMBIENT_TEMPERATURE);
    sensorArrayList.add(Sensor.TYPE_ORIENTATION);
    Log.i("SENSOR_ADD","addedSensor");

    //activateSensors();

}
    public void activateSensors() {
        for (int i = 0; i < sensorArrayList.size(); i++) {
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(sensorArrayList.get(i)),
                    android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    public void sensorRegister(){
        Log.i("WORKER", "SENDMESSAGEFUNCTION");
        mSensorManager.unregisterListener(this);
        //NetworkConnection network=new NetworkConnection();
        //network.sndFunc(.getDATA(),context_1);//send

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR ) {getValues(event,"TYPE_GAME_ROTATION_VECTOR");}
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE            ) {getValues(event,"TYPE_GYROSCOPE");}
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY              ) {getValues(event,"TYPE_GRAVITY");}
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION          ) {getValues(event,"TYPE_ORIENTATION");}
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY            ) {getValues(event,"TYPE_PROXIMITY");}
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER        ) {getValues(event, "TYPE_ACCELEROMETER");}
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR      ) {getValues(event,"TYPE_ROTATION_VECTOR");}
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION  ) {getValues(event,"TYPE_LINEAR_ACCELERATION");}
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE  ) {getValues(event,"TYPE_AMBIENT_TEMPERATURE");}
        if (event.sensor.getType() == Sensor.TYPE_LIGHT                ) {getValues(event,"TYPE_LIGHT");}
        //onSensorChanged(event);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    private void getValues(SensorEvent event, String type) {
        float[] values = event.values;
        float x = values[0];
        //float y = values[1];
        //float z = values[2];
        //Log.println(Log.DEBUG, "SENSOR_VALUES#########################\n#######################", Arrays.toString(values));
        fileManager1.addData((type+"@"+ Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()+"@@@"));
        SENSOR_DATA = SENSOR_DATA+(type+"@"+ Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()+"@@@");
        //Log.i("SENSORS", "ACCELEROMETER" + x +  " ");
    }
    public String getDATA(){
         Log.i("importantData",fileManager1.getData());
         Log.i("sensorData",SENSOR_DATA);
        return fileManager1.getData();
    }

}
