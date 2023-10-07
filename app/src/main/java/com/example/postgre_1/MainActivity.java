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
 * TYPE_GAME_ROTATION_VECTOR
 * TYPE_GYROSCOPE
 * TYPE_GRAVITY
 *TYPE_ACCELEROMETER
 * TYPE_ROTATION_VECTOR
 * TYPE_LINEAR_ACCELERATION
 * TYPE_AMBIENT_TEMPERATURE
 * TYPE_LIGHT
 *
 * */
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
        //FileManager1 fileManager=new FileManager1();

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

    public void msgFunc2() {
        ClientPart2 client = new ClientPart2();
        Log.i("WIFI", "BEGINING");
        try {
            Log.i("WIFI", "tryToSend");
            client.httpPostCommand1("hello".getBytes(), "192.168.1.197", 10);
            Log.i("WIFI", "Sended");
        } catch (IOException e) {
            Log.i("WIFI", "ErrorSend");
            throw new RuntimeException(e);
        }
        Log.i("WIFI", "END?????????????????????");
    }

    public void msgFunc() throws IOException {
        ClientPart client = new ClientPart();
        Log.i("WIFI", "tryToSend");
        sndFunc();
        Log.i("WIFI", "Sended");
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
        mInfo = new StringBuilder();

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAcc = new StringBuilder("акселерометр:");
            for (int valuesD = 0; valuesD <= event.values.length - 1; valuesD++) {
                mAcc.append("\n" + event.values[valuesD]);
            }
            String loacalStrToSend = strBuilder.AddData("mac", event.values);
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
    public void sndFunc() throws IOException {
        String postUrl = "https://192.168.1.197:80";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", "editTextEmail.getText().toString()");
            postData.put("password", "editTextPassword.getText().toString()");
            postData.put("rememberPassword", false);
            postData.put("ip_address", "1.41");
            postData.put("isCaptchaEnabled", false);

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
