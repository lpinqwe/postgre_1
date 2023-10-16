package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//todo change how FileManager work with NetworkConnection and SensorActivity
//todo make auto-send function, when WIFI is available
//todo change location of sensor listener, registrator etc.
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
public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    JSONObject postData = new JSONObject();
    List<Integer> sensorArrayList = new ArrayList<>();
    StringBuilder mInfo = new StringBuilder();
    StringBuilder mAcc = new StringBuilder("Акселерометр:");
    StringBuilder mLAcc = new StringBuilder("Линейный акселерометр:");
    StringDataBuilder strBuilder = new StringDataBuilder();
    String DATA;
    SensorActivity sensorsClass = new SensorActivity();


    public void activateSensors(List<Integer> sensorList) {
        for (int i = 0; i < sensorList.size(); i++) {
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(sensorList.get(i)),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorArrayList.add(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorArrayList.add(Sensor.TYPE_GYROSCOPE);
        sensorArrayList.add(Sensor.TYPE_GRAVITY);
        sensorArrayList.add(Sensor.TYPE_PROXIMITY);
        sensorArrayList.add(Sensor.TYPE_ACCELEROMETER);
        sensorArrayList.add(Sensor.TYPE_ROTATION_VECTOR);
        sensorArrayList.add(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorArrayList.add(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorArrayList.add(Sensor.TYPE_ORIENTATION);

        sensorsClass.importSensorList(sensorArrayList);
    }

    @Override
    protected void onStart() {
        activateSensors(sensorArrayList);
        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    protected void onPause() {

        try {
            Log.i("WORKER", "SENDMESSAGEFUNCTION");
            mSensorManager.unregisterListener(this);
            NetworkConnection network=new NetworkConnection();
            //network.sndFunc("new_STR_1", this);
            network.sndFunc(sensorsClass.getDATA(),this);//send
            //sndFunc("new_STR");//send

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorsClass.onSensorChanged(event);

    }

    /*
    public void onSensorChanged(SensorEvent event) {
        mInfo = new StringBuilder();

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAcc = new StringBuilder("акселерометр:");
            for (int valuesD = 0; valuesD <= event.values.length - 1; valuesD++) {
                mAcc.append("\n" + event.values[valuesD]);
            }
            String loacalStrToSend = strBuilder.AddData("mac", event.values);

        }
        mInfo.append(String.format("%1$s\n", mAcc));

        TextView tv_sensors = (TextView) findViewById(R.id.textView2);

        tv_sensors.setText(mInfo.toString());

    }
*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
