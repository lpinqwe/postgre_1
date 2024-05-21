package com.example.postgre_1;


import android.content.Context;
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

    dataWriterAndManager fileMNG = new dataWriterAndManager();

    DeviceUtils util_ = new DeviceUtils();
    SensorManagerClass mSensorManager = new SensorManagerClass(this, fileMNG);
    NetworkConnection network = new NetworkConnection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fileMNG.name= String.format( util_.getAndroidID(this));
        fileMNG.app_version="0.0_default";
        super.onCreate(savedInstanceState);

        mSensorManager.onCreateSensors();
        mSensorManager.activateSensors();

        setContentView(R.layout.activity_main);
        TextView tv=findViewById(R.id.textView2);
        tv.setText(fileMNG.app_version+"\n"+fileMNG.name);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        // operations to be performed
        // when user tap on the button
        if (button1 != null) {
            button1.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button", "button1");
                    //        fileManager1.addData((type+"@"+ Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()+"@@@"));
                    //fileMNG.addData(("$" + "BUTTON_1" + "@" + fileMNG.msgCurrentTime()));
                    float index[] = {1};
                    fileMNG.addJsonData("button",index,fileMNG.msgCurrentTime());
                }
            }));
        }
        if (button2 != null) {
            button2.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button", "button2");
                    //fileMNG.addData(("$" + "BUTTON_2" + "@" + fileMNG.msgCurrentTime()));
                    float index[] = {2};
                    fileMNG.addJsonData("button",index,fileMNG.msgCurrentTime());


                }
            }));
        }
        if (button3 != null) {
            button3.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button", "button3");
                    //fileMNG.addData(("$" + "BUTTON_3" + "@" + fileMNG.msgCurrentTime()));
                    float index[]={3};
                    fileMNG.addJsonData("button",index,fileMNG.msgCurrentTime());

                }
            }));
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

            //fileMNG.addData(mSensorManager.getDATA());
            network.sndFunc(fileMNG.getJsonData(), this);//send
            //mSensorManager.clearData();
            fileMNG.clearData();
            //mSensorManager.sensorRegister();
            //Log.i("INFO",mSensorManager.getDATA());
            //network.sndFunc(mSensorManager.getDATA(),this);//send

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onPause();
    }


}
