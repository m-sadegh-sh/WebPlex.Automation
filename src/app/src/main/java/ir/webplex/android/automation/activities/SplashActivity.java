package ir.webplex.android.automation.activities;

import android.os.Bundle;
import android.widget.TextView;

import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ActivityBase;
import ir.webplex.android.core.helpers.ExecutionUtils;
import ir.webplex.android.core.helpers.IntentUtils;
import ir.webplex.android.core.helpers.ServiceCacheManager;
import ir.webplex.android.core.logging.Logger;

public class SplashActivity extends ActivityBase {
    public static final String TAG = SplashActivity.class.getSimpleName();

    private TextView mPoweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        setContentView(R.layout.activity_splash);
        mPoweredBy = (TextView) findViewById(R.id.splash_powered_by);
        mPoweredBy.setText(String.format(String.valueOf(mPoweredBy.getText()), ServiceCacheManager.getPackageInfo().versionName));
        Logger.onViewLookedUp(TAG);

        ExecutionUtils.delayed(new Runnable() {
            @Override
            public void run() {
                Logger.onTransferring(TAG, SplashActivity.TAG, LoginActivity.TAG);
                IntentUtils.goToLogin(SplashActivity.this);
            }
        }, DELAYS_SPLASH);

        Logger.onExited(TAG, "onCreate");
    }

    @Override
    public void onBackPressed() {
    }
}
