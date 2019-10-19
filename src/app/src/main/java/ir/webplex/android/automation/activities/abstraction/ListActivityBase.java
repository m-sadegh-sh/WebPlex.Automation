package ir.webplex.android.automation.activities.abstraction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import ir.webplex.android.core.Constants;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.MessagingUitls;
import ir.webplex.android.core.helpers.ViewUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class ListActivityBase extends AppCompatActivity implements Constants {
    protected ListAdapter mAdapter;
    protected ListView mList;

    private Handler mHandler = new Handler();
    private boolean mFinishedStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.updateLayoutDirection(this);
        ViewUtils.updateActionBarTitle(this);
        ViewUtils.hideNativeActionBar(this);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRequestFocus);

        SessionManager.getInstance().clearNonPersisted();
        MessagingUitls.hideLatestToast();

        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };

    protected void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View emptyView = findViewById(android.R.id.empty);
        mList = (ListView) findViewById(android.R.id.list);

        if (mList == null)
            throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");

        if (emptyView != null)
            mList.setEmptyView(emptyView);

        mList.setOnItemClickListener(mOnClickListener);

        if (mFinishedStart)
            setListAdapter(mAdapter);

        mHandler.post(mRequestFocus);
        mFinishedStart = true;
    }

    public void setListAdapter(ListAdapter adapter) {
        synchronized (this) {
            mAdapter = adapter;
            mList.setAdapter(adapter);
        }
    }

    public ListView getListView() {
        return mList;
    }

    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView) parent, v, position, id);
        }
    };
}
