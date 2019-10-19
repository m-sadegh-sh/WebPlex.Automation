package ir.webplex.android.core.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.webplex.android.automation.AppController;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.ui.CustomTextView;

public class MessagingUitls {
    private static Toast _latestToast;

    public static void hideSoftInput(IBinder windowToken) {
        ServiceCacheManager.getInputMethodManager().hideSoftInputFromWindow(windowToken, 0);
    }

    public static void showToast(Activity activity, int resId, Object... args) {
        showToast(activity, activity.getString(resId), args);
    }

    public static void showToast(Activity activity, String text, Object... args) {
        hideLatestToast();

        View layout = InflatingUtils.inflate(R.layout.shared_toast, (ViewGroup) activity.findViewById(R.id.toast_root));

        CustomTextView message = (CustomTextView) layout.findViewById(R.id.toast_message);

        if (args != null && args.length > 0)
            text = String.format(text, args);
        message.setText(text);

        _latestToast = new Toast(activity);
        _latestToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        _latestToast.setDuration(Toast.LENGTH_LONG);
        _latestToast.setView(layout);

        _latestToast.show();
    }

    public static void hideLatestToast() {
        if (_latestToast != null) {
            _latestToast.setDuration(Toast.LENGTH_SHORT);
            _latestToast.cancel();
            _latestToast = null;
        }
    }

    public static void showProgress(ProgressDialog dialog, int resId) {
        hideLatestToast();

        dialog.setMessage(AppController.getInstance().getString(resId));

        if (!dialog.isShowing())
            dialog.show();
    }

    public static void hideProgress(ProgressDialog dialog) {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
