package supplyClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtils {
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }
    public static float[] getScreenResolution(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        float width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        float widthInches = displayMetrics.widthPixels / displayMetrics.xdpi;
        float heightInches = displayMetrics.heightPixels / displayMetrics.ydpi;
        return new float[]{width, height,widthInches,heightInches};
    }

}
