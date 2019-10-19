package ir.webplex.android.automation.activities.abstraction;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.webplex.android.core.Constants;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.MessagingUitls;
import ir.webplex.android.core.helpers.ViewUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class ActivityBase extends AppCompatActivity implements Constants {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.updateLayoutDirection(this);
        ViewUtils.updateActionBarTitle(this);
        ViewUtils.hideNativeActionBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SessionManager.getInstance().clearNonPersisted();
        MessagingUitls.hideLatestToast();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
