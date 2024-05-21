package supplyClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

public class DeviceUtils {
    @SuppressLint("HardwareIds")
    //todo read about annotations
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }
}
