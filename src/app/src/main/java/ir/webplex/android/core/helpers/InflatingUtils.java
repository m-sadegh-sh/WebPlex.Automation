package ir.webplex.android.core.helpers;

import android.view.View;
import android.view.ViewGroup;

public class InflatingUtils {
    public static View inflate(int resId) {
        return inflate(resId, null);
    }

    public static View inflate(int resId, ViewGroup root) {
        return ServiceCacheManager.getLayoutInflater().inflate(resId, root, false);
    }
}
