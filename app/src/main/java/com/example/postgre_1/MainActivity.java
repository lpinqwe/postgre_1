package com.example.postgre_1;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * sensors to read:
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
    List<Integer> sensorArrayList = new ArrayList<>();
    StringBuilder mInfo = new StringBuilder();
    StringBuilder mAcc = new StringBuilder("Акселерометр:");
    StringBuilder mLAcc = new StringBuilder("Линейный акселерометр:");
    StringDataBuilder strBuilder = new StringDataBuilder();
    JSONObject postData = new JSONObject();
    SensorActivity sensors = new SensorActivity();

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
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
//ToDo: lambda expression for this shit
//ToDo: dont forger to register sensors
        /*
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
         accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
          magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
           sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
         sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
        * */


        //FileManager1 fileManager=new FileManager1();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    public void msgFunc() throws IOException {
        sndFunc();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        Log.i("WIFI", "BEGINING");
        try {
            msgFunc();
        } catch (IOException e) {
            Log.i("WIFI", "ERR");
            throw new RuntimeException(e);
        }
        Log.i("WIFI", "END?????????????????????");
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("SENSOR", "ZERO POINT_+_+_+_+_++__+_+_++_+__+_+_+_+_+_+_+");
        sensors.onSensorChanged(event);

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

    public void sndFunc() throws IOException {
        String postUrl = "http://192.168.1.197:80";
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        try {
            postData.put("emailVolodar", "editTextEmail.getText().toString()");
            // postData.put("password", "editTextPassword.getText().toString()");
            // postData.put("rememberPassword", false);
            // postData.put("ip_address", "1.41");
            // postData.put("isCaptchaEnabled", false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_LONG).show();
                Log.i("WIFI_VOLLEY", "Response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
