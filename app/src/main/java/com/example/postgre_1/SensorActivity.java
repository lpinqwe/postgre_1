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
    FileManager1 fileManager1= new FileManager1();

    public void importSensorList(List<Integer> list) {
        sensorsList = list;
    }

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
     *
     * */
    @Override
    public void onSensorChanged(SensorEvent event) {


        //Log.i("SENSOR", "POINT TWO ###########");
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
   }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Log.i("SENSOR", "accuracyChanged");
    }

    private void getValues(SensorEvent event, String type) {
        float[] values = event.values;
        //float x = values[0];
        //float y = values[1];
        //float z = values[2];
        for (int i = 0; i < values.length; i++) {
            //save data+type
        }
        //Log.println(Log.DEBUG, "SENSOR_VALUES#########################\n#######################", Arrays.toString(values));
        fileManager1.addData((type+"@"+Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()+"@@@"));
        // Log.i("SENSORS", "ACCELEROMETER" + x + " " + y + " " + z);
    }
    public String getDATA(){
        return fileManager1.getData();
    }
}
