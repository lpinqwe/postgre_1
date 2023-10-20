package com.example.postgre_1;
import android.widget.Toast;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

import java.io.IOException;

import static android.app.PendingIntent.getActivity;


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
public class MainActivity extends AppCompatActivity {
      //  SensorManagerClass mSensorManager = new SensorManagerClass(this);

    // NetworkConnection network=new NetworkConnection();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButtonClass buttons = new ButtonClass(this);
        //Button buttona, buttonb,button3;
        //buttonb = findViewById(R.id.button2);
        //buttons.activateButton();
        //mSensorManager.onCreateSensors();
        //mSensorManager.activateSensors();
        Button button = (Button)findViewById(R.id.button1);

        // operations to be performed
        // when user tap on the button
        if (button != null) {
            button.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button","button");
                    CharSequence text = "Hello toast!";
                    // displaying a toast message
                    Toast.makeText(MainActivity.this, "(String)text", Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

    @Override
    protected void onStart() {
        Log.i("Service","ZERO_POINT");

        startService(new Intent( this, ServiceForeground.class ) );

        super.onStart();
    }

    @Override
    protected void onResume() {
        //mSensorManager.sensorRegister();
        super.onResume();
    }


    @Override
    protected void onPause() {
       /*
        try {
            mSensorManager.sensorRegister();
            network.sndFunc(mSensorManager.getDATA(),this);//send
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
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
