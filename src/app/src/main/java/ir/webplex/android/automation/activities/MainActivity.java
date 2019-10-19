package ir.webplex.android.automation.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import ir.webplex.android.api.parameters.Permission;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ActivityBase;
import ir.webplex.android.automation.ui.CustomTextView;
import ir.webplex.android.automation.ui.DrawerProvider;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.InflatingUtils;
import ir.webplex.android.core.helpers.ViewUtils;
import ir.webplex.android.core.logging.Logger;

public class MainActivity extends ActivityBase {
    public static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private CustomTextView mTipsSubmitReceipt;
    private ListView mDrawerList;
    private DrawerProvider mDrawerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        moveDrawerToTop();
        mDrawerProvider = new DrawerProvider(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_decorator_root);
        mTipsSubmitReceipt = (CustomTextView) findViewById(R.id.main_tips_submit_receipt);
        mDrawerList = (ListView) findViewById(R.id.main_drawer);
        Logger.onViewLookedUp(TAG);

        mDrawerProvider.setComponents(mDrawerLayout, mDrawerList);

        mTipsSubmitReceipt.setVisibility(SessionManager.getInstance().hasPermission(Permission.LetterReceiptRegistrar) ? View.VISIBLE : View.GONE);

        Logger.onExited(TAG, "onCreate");
    }

    private void moveDrawerToTop() {
        DrawerLayout decorator = (DrawerLayout) InflatingUtils.inflate(R.layout.activity_main_decorator);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout drawerPlaceholder = (LinearLayout) decorator.findViewById(R.id.main_drawer_placeholder);
        drawerPlaceholder.addView(child, 0);
        decorator.findViewById(R.id.main_drawer).setPadding(0, ViewUtils.getStatusBarHeight(), 0, 0);

        decor.addView(decorator);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerProvider.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerProvider.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerProvider.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
