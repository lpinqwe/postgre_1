package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONObject;

import java.io.IOException;


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
public class MainActivity extends Activity {
        SensorManagerClass mSensorManager = new SensorManagerClass(this);

        NetworkConnection network=new NetworkConnection();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager.onCreateSensors();
        mSensorManager.activateSensors();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        //mSensorManager.sensorRegister();
        super.onResume();
    }


    @Override
    protected void onPause() {
        try {
            mSensorManager.sensorRegister();
            network.sndFunc(mSensorManager.getDATA(),this);//send
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onPause();
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


}
