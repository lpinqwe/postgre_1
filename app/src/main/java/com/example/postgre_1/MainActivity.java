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
FileManager1 fileMNG = new FileManager1();
NetworkConnection network = new NetworkConnection();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);

        // operations to be performed
        // when user tap on the button
        if (button1 != null) {
            button1.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button","button1");
                    //        fileManager1.addData((type+"@"+ Arrays.toString(values)+"@"+fileManager1.msgCurrentTime()+"@@@"));
                    fileMNG.addData(("$"+"BUTTON_1"+"@"+fileMNG.msgCurrentTime()+"&"));
                }
            }));
        }
        if (button2 != null) {
            button2.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button","button2");
                    fileMNG.addData(("$"+"BUTTON_2"+"@"+fileMNG.msgCurrentTime()));

                }
            }));
        }
        if (button3 != null) {
            button3.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button","button3");
                    fileMNG.addData(("$"+"BUTTON_3"+"@"+fileMNG.msgCurrentTime()));

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
        try {
            network.sndFunc(fileMNG.getData(), this);//send
            fileMNG.clearData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onPause();
    }



}
