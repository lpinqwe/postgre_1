package com.example.postgre_1;

import android.provider.Settings;
import android.provider.Settings.Secure;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import supplyClasses.DeviceUtils;
import supplyClasses.NetworkConnection;


import java.io.IOException;


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
    dataWriterAndManager fileMNG ;

    SensorManagerClass mSensorManager;
    NetworkConnection network = new NetworkConnection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        fileMNG = new dataWriterAndManager(mId);
        mSensorManager=new SensorManagerClass(this, fileMNG);

        fileMNG.name = String.format(DeviceUtils.getAndroidID(this));
        fileMNG.app_version = "0.0_default";
        super.onCreate(savedInstanceState);

        mSensorManager.onCreateSensors();
        mSensorManager.activateSensors();

        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.textView2);
        tv.setText(String.format("%s\n%s", fileMNG.app_version, fileMNG.name));
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        // operations to be performed
        // when user tap on the button
        if (button1 != null) {
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {
                    Log.i("button", "button1");
                    float[] index = {1};
                    fileMNG.addJsonData("button", index, fileMNG.msgCurrentTime());
                }
            });
        }
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {
                    Log.i("button", "button2");
                    float[] index = {2};
                    fileMNG.addJsonData("button", index, fileMNG.msgCurrentTime());


                }
            });
        }
        if (button3 != null) {
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {
                    Log.i("button", "button3");
                    float[] index = {3};
                    fileMNG.addJsonData("button", index, fileMNG.msgCurrentTime());

                }
            });
        }
    }

    @Override
    protected void onStart() {
        Log.i("Service", "ZERO_POINT");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        try {
            Log.i("PAUSE", String.format("PAUSE DATA SEND %d", System.currentTimeMillis()));
            mSensorManager.sensorRegister();
            network.sndFunc(fileMNG.getJsonData(), this);
            fileMNG.clearData();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onPause();
    }


}
