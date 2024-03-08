package com.example.postgre_1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

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
   // String SENSOR_DATA = "";
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
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("WORKER", "sensors changed");

        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR ) {
            eventValues(event,"TYPE_GAME_ROTATION_VECTOR");}
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE            ) {
            eventValues(event,"TYPE_GYROSCOPE");}
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY              ) {
            eventValues(event,"TYPE_GRAVITY");}
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION          ) {
            eventValues(event,"TYPE_ORIENTATION");}
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY            ) {
            eventValues(event,"TYPE_PROXIMITY");}
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER        ) {
            eventValues(event, "TYPE_ACCELEROMETER");}
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR      ) {
            eventValues(event,"TYPE_ROTATION_VECTOR");}
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION  ) {
            eventValues(event,"TYPE_LINEAR_ACCELERATION");}
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE  ) {
            eventValues(event,"TYPE_AMBIENT_TEMPERATURE");}
        if (event.sensor.getType() == Sensor.TYPE_LIGHT                ) {
            eventValues(event,"TYPE_LIGHT");}
        //onSensorChanged(event);//looped looop
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    private void eventValues(SensorEvent event, String type) {
        float[] values = event.values;
        float x = values[0];
        fileManager1.addData(("$"+type+"@"+ Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()));
        //SENSOR_DATA = SENSOR_DATA+("$"+type+"@"+ Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()+"@@@");
    }
    public void addData(String data){
        fileManager1.addData(("$"+data+"@"+fileManager1.msgCurrentTime()));

    }
    public String getDATA(){
         Log.i("importantData",fileManager1.getData());
        // Log.i("sensorData",SENSOR_DATA);
        return fileManager1.getData();
    }
    public void clearData(){
        //SENSOR_DATA="";
        fileManager1.clearData();
        Log.i("importantDataClear",fileManager1.getData());

       // Log.i("sensorDataClear",SENSOR_DATA);

    }
    //todo make it better....


}
