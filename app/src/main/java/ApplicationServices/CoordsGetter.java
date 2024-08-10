package ApplicationServices;


import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.example.postgre_1.SensorManagerClass;
import com.example.postgre_1.dataWriterAndManager;
import supplyClasses.ConfigClass;
import supplyClasses.DeviceUtils;
import supplyClasses.NetworkConnection;

import java.io.IOException;

public class CoordsGetter extends AccessibilityService {
    dataWriterAndManager fileMNG1 ;

    SensorManagerClass mSensorManager1;
    NetworkConnection network1 = new NetworkConnection();
    ConfigClass conf = new ConfigClass();

    void senderFunc() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                synchronized (fileMNG1) {
                                    network1.sndFunc(fileMNG1.getJsonData(), getApplicationContext());
                                    fileMNG1.clearData();
                                }
                            } catch (IOException e) {
                                e.printStackTrace(); // Логирование ошибки
                            }
                            Log.e("service", "service foreground running");
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
        mSensorManager1=new SensorManagerClass(this, fileMNG1);
        fileMNG1.name = String.format(DeviceUtils.getAndroidID(this));

        mSensorManager1.onCreateSensors();
        mSensorManager1.activateSensors();
        super.onServiceConnected();
        Log.d("CoordsGetter", "Service connected");
        senderFunc();
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("TouchLoggingService", "Event triggered: " + event.getEventType());

        // Слушаем события клика и длинного клика
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED ||
                event.getEventType() == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED||
                event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED
        ) {

            // Получаем AccessibilityNodeInfo, представляющий элемент, с которым взаимодействовали
            AccessibilityNodeInfo source = event.getSource();
            if (source != null) {
                // Получаем положение элемента на экране
                Rect bounds = new Rect();
                source.getBoundsInScreen(bounds);

                // Определяем координаты центра элемента (X, Y)
                int x = bounds.centerX();
                int y = bounds.centerY();

                // Логируем координаты
                Log.i("TouchLoggingService", "Button clicked at: (" + x + ", " + y + ")");

                // Здесь можно добавить код для сохранения или отправки данных
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Обработка прерывания (если требуется)
    }
}
