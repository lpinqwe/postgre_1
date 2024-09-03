package ApplicationServices;


import android.accessibilityservice.AccessibilityService;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.example.postgre_1.SensorManagerClass;
import com.example.postgre_1.dataWriterAndManager;
import supplyClasses.ConfigClass;
import supplyClasses.DeviceUtils;
import supplyClasses.NetworkConnection;

import java.io.IOException;
import java.util.Arrays;

public class CoordsGetter extends AccessibilityService {
    dataWriterAndManager fileMNG1;
    SensorManagerClass mSensorManager1;
    NetworkConnection network1 = new NetworkConnection();
    ConfigClass conf = new ConfigClass();

    private WindowManager windowManager;
    private View touchInterceptor;
    GestureDetector gestureDetector;
    private static final String CHANNEL_ID = "CoordsGetterOverlayChannel";
    private static final String LOG_TAG = "CoordsGetterOverlay";


    void senderFunc() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                if (mSensorManager1.isScreenUnlocked() == true) {
                                    synchronized (fileMNG1) {
                                        network1.sndFunc(fileMNG1.getJsonData(), getApplicationContext());
                                        fileMNG1.clearData();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace(); // Логирование ошибки
                            }
                            //Log.e("service", "service foreground running");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
    }

    @Override
    protected void onServiceConnected() {
        String mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        fileMNG1 = new dataWriterAndManager(mId);
        mSensorManager1 = new SensorManagerClass(this, fileMNG1);
        fileMNG1.name = String.format(DeviceUtils.getAndroidID(this));

        mSensorManager1.onCreateSensors();
        mSensorManager1.activateSensors();
        super.onServiceConnected();
        Log.d("CoordsGetter", "Service connected");
        senderFunc();

        this.gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                float x = e.getRawX();
                float y = e.getRawY();
                Log.d(LOG_TAG, "Single tap up at: (" + x + ", " + y + ")");
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                float x = e.getRawX();
                float y = e.getRawY();
                Log.d(LOG_TAG, "Long press at: (" + x + ", " + y + ")");
                super.onLongPress(e);
            }
        });
        touchInterceptor = new View(this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params;
        params = new WindowManager.LayoutParams(
                1, // Установите размер окна немного больше 1x1 пиксель
                1, // Установите размер окна немного больше 1x1 пиксель
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        windowManager.addView(touchInterceptor, params);

        // Установка OnTouchListener для перехвата касаний
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float [] tmp = {event.getRawX(),event.getRawY()};
                //float x = event.getRawX();
                //float y = event.getRawY();
                //Log.d("overlay XY", "x=" + x + "y=" + y);
                // Логирование всех типов событий
                Log.d("SWITCH", "overlay outside at: (" + Arrays.toString(tmp) + ")");
                // Передача события GestureDetector для дополнительной обработки
                //gestureDetector.onTouchEvent(event);

                fileMNG1.addJsonData("onTouch_event", tmp, fileMNG1.msgCurrentTime());

                return false; // Возвращаем false, чтобы передать событие дальше
            }
        });


    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("ACCESSIBILITY SERVICE", "AS Event triggered: " + event.getEventType());

        // Слушаем события клика и длинного клика
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED ||
                event.getEventType() == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED
        ) {

            // Получаем AccessibilityNodeInfo, представляющий элемент, с которым взаимодействовали
            AccessibilityNodeInfo source = event.getSource();

            if (source != null) {
                // Получаем положение элемента на экране
                Rect bounds = new Rect();
                source.getBoundsInScreen(bounds);

                // Определяем координаты центра элемента (X, Y)
                float[]tmp={bounds.bottom, bounds.left,bounds.right,bounds.top};
                // Логируем координаты
                Log.d("ACCESSIBILITY SERVICE", "element clicked at: (" + Arrays.toString(tmp) +")");
                fileMNG1.addJsonData("button_bound_screen", tmp, fileMNG1.msgCurrentTime());

                // Здесь можно добавить код для сохранения или отправки данных
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Обработка прерывания (если требуется)
    }
}
