package com.example.postgre_1;

import ApplicationServices.CoordsGetter;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.accessibility.AccessibilityManager;
import supplyClasses.ConfigClass;
import android.provider.Settings;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import supplyClasses.DeviceUtils;
import supplyClasses.NetworkConnection;

//AccessibilityService --фигня
//windowManager к пробе
import java.io.IOException;
import java.util.List;

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
    dataWriterAndManager fileMNG;

    SensorManagerClass mSensorManager;
    NetworkConnection network = new NetworkConnection();
    ConfigClass conf = new ConfigClass();

    //todo read about simple config
    //todo write button proc
//TODO send msg with params of screen {username,event-ecranParams,time0,[value]}
    // Метод для проверки, включена ли служба Accessibility
    private boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            if (enabledService.getId().equals(context.getPackageName() + "/" + service.getName())) {
                return true;
            }
        }
        return false;
    }

    // Метод для показа диалога с запросом у пользователя включить службу
    private void showAccessibilityServiceRequestDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Accessibility Service Required")
                .setMessage("Please enable the Accessibility Service to use this feature.")
                .setPositiveButton("Enable", (dialog, which) -> {
                    // Открываем настройки Accessibility для включения службы
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Обрабатываем отказ пользователя (если требуется)
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        fileMNG = new dataWriterAndManager(mId);



        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {
            // Показать запрос
            fileMNG.name = String.format(DeviceUtils.getAndroidID(this));

            float[]tmp =DeviceUtils.getScreenResolution(this);
            fileMNG.addJsonData("physical_screen", tmp, fileMNG.msgCurrentTime());

            // Установить флаг в false, чтобы больше не показывать запрос
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();
        }

        if (!isAccessibilityServiceEnabled(this, CoordsGetter.class)) {
            // Если не включена, показываем диалог для запроса у пользователя
            showAccessibilityServiceRequestDialog();
        }

            setContentView(R.layout.activity_main);

            TextView tv = findViewById(R.id.textView2);
            tv.setText(String.format("%stest app\ndevice name:%s", conf.version, fileMNG.name));

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
            //mSensorManager.sensorRegister();
            network.sndFunc(fileMNG.getJsonData(), this);
            fileMNG.clearData();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onPause();
    }


}
