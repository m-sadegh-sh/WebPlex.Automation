package ir.webplex.android.automation.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ir.webplex.android.api.parameters.Permission;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.MainActivity;
import ir.webplex.android.automation.adapters.DrawerAdapter;
import ir.webplex.android.automation.models.DrawerItem;
import ir.webplex.android.automation.models.NavigableItem;
import ir.webplex.android.automation.models.ProfileItem;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.IntentUtils;

public class DrawerProvider {
    private MainActivity mActivity;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private MainDrawerListener mDrawerListener;

    public DrawerProvider(MainActivity activity) {
        mActivity = activity;
        mActionBar = mActivity.getSupportActionBar();
    }

    public void setComponents(DrawerLayout drawerLayout, ListView drawerList) {
        mDrawerLayout = drawerLayout;
        mDrawerList = drawerList;

        mDrawerListener = new MainDrawerListener(mActivity, mDrawerLayout);
        mDrawerLayout.setDrawerListener(mDrawerListener);

        configureAdapter();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new DrawerListItemClickListener());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerListener.onOptionsItemSelected(item);
    }

    public void syncState() {
        mDrawerListener.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerListener.onConfigurationChanged(newConfig);
    }

    private void displayView(long id) {
        switch ((int) id) {
            case R.integer.navigable_profile:
                return;
            case R.integer.navigable_main:
                break;
            case R.integer.navigable_submit_receipt:
                IntentUtils.goToSubmitReceipt(mActivity);
                break;
            case R.integer.navigable_logout:
                SessionManager.getInstance().signOut();
                IntentUtils.goToLogin(mActivity);
                break;
        }

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void configureAdapter() {
        ArrayList<DrawerItem> items = new ArrayList<>();

        items.add(new ProfileItem(
                R.integer.navigable_profile,
                SessionManager.getInstance().getTitle(),
                SessionManager.getInstance().getEmail(),
                SessionManager.getInstance().getAvatar()
        ));

        items.add(new NavigableItem(
                R.integer.navigable_main,
                mActivity.getString(R.string.navigable_home),
                R.mipmap.ic_navigable_main
        ));

        if (SessionManager.getInstance().hasPermission(Permission.LetterReceiptRegistrar)) {
            items.add(new NavigableItem(
                    R.integer.navigable_submit_receipt,
                    mActivity.getString(R.string.navigable_submit_receipt),
                    R.mipmap.ic_navigable_submit_receipt
            ));
        }

        items.add(new NavigableItem(
                R.integer.navigable_logout,
                mActivity.getString(R.string.navigable_logout),
                R.mipmap.ic_navigable_logout,
                true,
                false
        ));

        mDrawerList.setAdapter(new DrawerAdapter(items));
    }

    private class DrawerListItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(id);
        }
    }

    private class MainDrawerListener extends ActionBarDrawerToggle {
        public MainDrawerListener(Activity activity, DrawerLayout drawerLayout) {
            super(activity, drawerLayout, R.string.application_label, R.string.application_label);
        }

        public void onDrawerClosed(View view) {
            mActivity.invalidateOptionsMenu();
        }

        public void onDrawerOpened(View drawerView) {
            mActivity.invalidateOptionsMenu();
        }
    }
}
