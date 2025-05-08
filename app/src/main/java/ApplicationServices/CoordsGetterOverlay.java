package ApplicationServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.*;

public class CoordsGetterOverlay extends Service {

    private WindowManager windowManager;
    private View touchInterceptor;
    private GestureDetector gestureDetector;
    private static final String CHANNEL_ID = "CoordsGetterOverlayChannel";
    private static final String LOG_TAG = "CoordsGetterOverlay";

    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "CoordsGetter Overlay Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("CoordsGetter Overlay")
                .setContentText("Recording touch events.")
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Временно используем стандартный значок
                .build();
        startForeground(1, notification);
        Log.d(LOG_TAG, "Foreground service started");

        // Инициализация GestureDetector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
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

        // Создание Overlay (наложения) для перехвата событий касания
        touchInterceptor = new View(this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params;
//
//        params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Попробуйте изменить тип
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
//                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//                PixelFormat.TRANSLUCENT
//        );




 //       params.gravity = Gravity.LEFT | Gravity.TOP;


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
                float x = event.getRawX();
                float y = event.getRawY();
                Log.d("COORDXY","x="+x+"y="+y);
                // Логирование всех типов событий
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(LOG_TAG, "Action down at: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(LOG_TAG, "Action move at: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(LOG_TAG, "Action up at: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(LOG_TAG, "Action cancel at: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                        Log.d(LOG_TAG, "Action outside at: (" + x + ", " + y + ")");
                        break;
                }

                // Передача события GestureDetector для дополнительной обработки
                gestureDetector.onTouchEvent(event);

                return false; // Возвращаем false, чтобы передать событие дальше
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (touchInterceptor != null) windowManager.removeView(touchInterceptor);
    }

    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public android.os.IBinder onBind(android.content.Intent intent) {
        return null;
    }
}
