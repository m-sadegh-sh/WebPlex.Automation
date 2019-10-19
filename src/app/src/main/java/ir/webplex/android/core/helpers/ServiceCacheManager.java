package ir.webplex.android.core.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import ir.webplex.android.automation.AppController;

public final class ServiceCacheManager {
    private static LayoutInflater mLayoutInflater;
    private static InputMethodManager mInputMethodManager;
    private static PackageManager mPackageManager;
    private static PackageInfo mPackageInfo;

    public static synchronized InputMethodManager getInputMethodManager() {
        if (mInputMethodManager == null)
            mInputMethodManager = (InputMethodManager) AppController.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);

        return mInputMethodManager;
    }

    public static synchronized LayoutInflater getLayoutInflater() {
        if (mLayoutInflater == null)
            mLayoutInflater = (LayoutInflater) AppController.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return mLayoutInflater;
    }

    public static synchronized PackageInfo getPackageInfo() {
        if (mPackageManager == null) {
            mPackageManager = AppController.getInstance().getPackageManager();

            try {
                mPackageInfo = mPackageManager.getPackageInfo(AppController.getInstance().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return mPackageInfo;
    }
}
