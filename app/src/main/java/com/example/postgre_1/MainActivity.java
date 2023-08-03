package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    List<Integer> sensorArrayList = new ArrayList<>();
    StringBuilder mInfo = new StringBuilder();
    StringBuilder mAcc = new StringBuilder("Акселерометр:");
    StringBuilder mLAcc = new StringBuilder("Линейный акселерометр:");
    Set<String> s = new HashSet<String>();
    StringDataBuilder strBuilder = new StringDataBuilder();
    //PostgreSend postgreSend1=new PostgreSend();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorArrayList.add(Sensor.TYPE_ACCELEROMETER);
        sensorArrayList.add(Sensor.TYPE_GRAVITY);
        sensorArrayList.add(Sensor.TYPE_GYROSCOPE);
        sensorArrayList.add(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        sensorArrayList.add(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorArrayList.add(Sensor.TYPE_ROTATION_VECTOR);
        sensorArrayList.add(Sensor.TYPE_SIGNIFICANT_MOTION);
        sensorArrayList.add(Sensor.TYPE_STEP_COUNTER);
        sensorArrayList.add(Sensor.TYPE_STEP_DETECTOR);
        FileManager1 fileManager=new FileManager1();

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            s.add(Build.BRAND);
            s.add(Build.DEVICE);
            s.add(Build.ID);
            s.add(Build.FINGERPRINT);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        sensorArrayList.parallelStream().forEach(
                sensor -> {
                    mSensorManager.registerListener(this,
                            mSensorManager.getDefaultSensor(sensor),
                            SensorManager.SENSOR_DELAY_UI);
                });
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mInfo = new StringBuilder();

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAcc = new StringBuilder("акселерометр:");
            for(int valuesD = 0; valuesD <=event.values.length-1; valuesD++){
                mAcc.append("\n" + event.values[valuesD]);
            }
            String loacalStrToSend=strBuilder.AddData("mac",event.values);
            //System.out.println(loacalStrToSend);
            //postgreSend1.sendData(loacalStrToSend);
            /*
            mAcc.append("\n" + event.values[0] + "\n");
            mAcc.append(event.values[1] + "\n");
            mAcc.append(event.values[2] + "\n");
            */
        }
/*
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            mAcc = new StringBuilder("Linear:");
            mAcc.append("\n" + event.values[0] + "\n");
            mAcc.append(event.values[1] + "\n");
            mAcc.append(event.values[2] + "\n");

        }
        */


        mInfo.append(String.format("%1$s\n", mAcc));

        TextView tv_sensors = (TextView) findViewById(R.id.textView2);

        tv_sensors.setText(mInfo.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
