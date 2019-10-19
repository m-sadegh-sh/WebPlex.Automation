package ir.webplex.android.automation;

import android.app.Application;
import android.text.TextUtils;

import ir.webplex.android.automation.ui.TypefaceId;
import ir.webplex.android.core.Constants;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.logging.Logger;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application implements Constants {
    private static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.onEntering(TAG, "onCreate");

        mInstance = this;

        CalligraphyConfig calligraphyConfig = new CalligraphyConfig
                .Builder()
                .setDefaultFontPath(String.format("fonts/%s.ttf", TypefaceId.IRANIAN_SANS_REGULAR))
                .setFontAttrId(R.attr.fontPath)
                .build();

        CalligraphyConfig.initDefault(calligraphyConfig);

        if (TextUtils.isEmpty(SessionManager.getInstance().getBaseUri()))
            SessionManager.getInstance().setBaseUri(API_URIS_BASE, true);

        Logger.onExited(TAG, "onCreate");
    }
}
