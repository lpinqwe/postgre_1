package supplyClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
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
        return new float[]{width, height, widthInches, heightInches};
    }


    /**
     * This class uses the AccountManager to get the primary email address of the
     * current user.
     */

    public static String getEmail(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType("com.google");

        if (accounts.length > 0) {
            // Возвращаем первый найденный адрес электронной почты
            return accounts[0].name;
        } else {
            return "null";
        }
    }
}