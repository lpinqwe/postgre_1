package com.example.postgre_1;

import ApplicationServices.CoordsGetter;
import ApplicationServices.CoordsGetterOverlay;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
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

    //todo write button proc
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

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isServiceEnabled = isAccessibilityServiceEnabled(this, CoordsGetter.class);
//        Intent serviceIntent = new Intent(this, CoordsGetterOverlay.class);
//        if (!isServiceRunning(CoordsGetterOverlay.class)) {
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + getPackageName()));
//            startActivityForResult(intent, 1234);
//
//            startForegroundService(serviceIntent);
//            Log.d("service start", "overlay ser main");
//        }

        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);
        SharedPreferences devicename = getSharedPreferences("name_device", Context.MODE_PRIVATE);

        if (!isServiceEnabled) {
            SharedPreferences.Editor editor = devicename.edit();
            String mId = DeviceUtils.getEmail(this);
            if (mId == "null") {
                mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            }


            editor.putString("name_device", mId);
            editor.apply();
        }

        fileMNG = new dataWriterAndManager(devicename.getString("name_device", "null"));


        if (isFirstTime) {
            // Показать запрос
            fileMNG.name = String.format(DeviceUtils.getAndroidID(this));

            float[] tmp = DeviceUtils.getScreenResolution(this);
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
                    Log.d("button_clalibrate", "button1");
                    float[] index = {1};
                    fileMNG.addJsonData("button_clalibrate", index, fileMNG.msgCurrentTime());
                }
            });
        }
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {
                    Log.d("button_clalibrate", "button2");
                    float[] index = {2};
                    fileMNG.addJsonData("button_clalibrate", index, fileMNG.msgCurrentTime());


                }
            });
        }
        if (button3 != null) {
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {
                    Log.d("button_clalibrate", "button3");
                    float[] index = {3};
                    fileMNG.addJsonData("button_clalibrate", index, fileMNG.msgCurrentTime());

                }
            });
        }
    }


    @Override
    protected void onStart() {
        Log.d("Service", "ZERO_POINT");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        try {
            Log.d("PAUSE", String.format("PAUSE DATA SEND %d", System.currentTimeMillis()));
            //mSensorManager.sensorRegister();
            network.sndFunc(fileMNG.getJsonData(), this);
            fileMNG.clearData();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onPause();
    }


}
