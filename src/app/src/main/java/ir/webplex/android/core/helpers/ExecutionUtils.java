package ir.webplex.android.core.helpers;

import android.os.Handler;

public class ExecutionUtils {
    public static void delayed(Runnable runnable, int delay) {
        new Handler().postDelayed(runnable, delay);
    }
}
