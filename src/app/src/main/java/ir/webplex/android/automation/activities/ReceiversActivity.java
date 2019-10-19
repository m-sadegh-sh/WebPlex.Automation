package ir.webplex.android.automation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.webplex.android.api.ServiceBuilder;
import ir.webplex.android.api.requests.FindReceiversRequest;
import ir.webplex.android.api.responses.FindReceiversResponse;
import ir.webplex.android.api.utils.CancelableCallback;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ListActivityBase;
import ir.webplex.android.automation.adapters.InfiniteScrollListener;
import ir.webplex.android.automation.adapters.ReceiversAdapter;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.automation.ui.CustomTextView;
import ir.webplex.android.automation.ui.CustomTextWatcher;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.IntentUtils;
import ir.webplex.android.core.helpers.MappingUtils;
import ir.webplex.android.core.logging.Logger;

public class ReceiversActivity extends ListActivityBase {
    public static final String TAG = ReceiversActivity.class.getSimpleName();

    private ActionBar mActionBar;
    private EditText mQuery;
    private MenuItem mClear;
    private RelativeLayout mProgressWrapper;
    private RelativeLayout mProgressMessageWrapper;
    private CustomTextView mProgressMessage;

    private LetterItem mSelectedLetterInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        setContentView(R.layout.activity_receivers);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.bar_receivers);

        mQuery = (EditText) mActionBar.getCustomView().findViewById(R.id.bar_receivers_query);
        mProgressWrapper = (RelativeLayout) findViewById(R.id.receivers_progress_wrapper);
        mProgressMessageWrapper = (RelativeLayout) findViewById(R.id.receivers_progress_message_wrapper);
        mProgressMessage = (CustomTextView) findViewById(R.id.receivers_progress_message);
        Logger.onViewLookedUp(TAG);

        mQuery.addTextChangedListener(QueryTextChangedListener);
        mQuery.requestFocus();

        mSelectedLetterInstance = IntentUtils.getSelectedLetter(getIntent());
        getListView().setOnScrollListener(OnScrollListener);

        onFindingReceivers(false);

        Logger.onExited(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_receivers, menu);
        mClear = menu.findItem(R.id.bar_receivers_clear);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_receivers_clear:
                if (!TextUtils.isEmpty(mQuery.getText()))
                    mQuery.setText(null);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onFindingReceivers(boolean fromBeginning) {
        CancelableCallback.cancelAll(TAG);

        if (fromBeginning) {
            ReceiversAdapter adapter = ((ReceiversAdapter) getListAdapter());
            if (adapter != null)
                adapter.clearItems();

            OnScrollListener.reset();
        }

        mProgressMessageWrapper.setVisibility(View.INVISIBLE);
        mProgressWrapper.setVisibility(View.VISIBLE);

        String query = String.valueOf(mQuery.getText());
        int pageIndex = OnScrollListener.getCurrentPageIndex() + 1;
        int pageSize = OnScrollListener.getPageSize();

        FindReceiversRequest request = new FindReceiversRequest(
                SessionManager.getInstance().getToken(),
                mSelectedLetterInstance.getLetterGuid(),
                query,
                pageIndex,
                pageSize
        );
        Logger.onFindingReceivers(TAG, request);

        ServiceBuilder
                .getInstance()
                .getSearchService()
                .findReceivers(request)
                .enqueue(new FindReceiversResponseCallback(TAG));
    }


    private CustomTextWatcher QueryTextChangedListener = new CustomTextWatcher() {
        @Override
        public void afterTextChanged(String text) {
            mClear.setEnabled(!TextUtils.isEmpty(text));
            onFindingReceivers(true);
        }
    };

    private class FindReceiversResponseCallback extends CancelableCallback<FindReceiversResponse> {
        public FindReceiversResponseCallback(String tag) {
            super(tag);
        }

        @Override
        public void onSuccess(FindReceiversResponse result) {
            Logger.onFindReceiversResponse(TAG, result);

            mProgressWrapper.setVisibility(View.INVISIBLE);

            if (result.getCount() > 0)
                MappingUtils.updateAdapter(ReceiversActivity.this, result);
            else if (OnScrollListener.isInFirstPage()) {
                mProgressMessageWrapper.setVisibility(View.VISIBLE);
                mProgressMessage.setText(R.string.receivers_error_no_results);
            }

            OnScrollListener.setHasMorePage(result.hasMorePage());
        }

        @Override
        public void onError(Throwable error) {
            Logger.onError(TAG, error);

            mProgressWrapper.setVisibility(View.INVISIBLE);
            mProgressMessageWrapper.setVisibility(View.VISIBLE);
            mProgressMessage.setText(R.string.apis_error_invalid_response);

            OnScrollListener.setHasMorePage(false);
        }
    }

    private InfiniteScrollListener OnScrollListener = new InfiniteScrollListener(API_PARAMETERS_SEARCH_FIND_RECEIVERS_PAGE_SIZE) {
        @Override
        public void onLoadNextPage() {
            onFindingReceivers(false);
        }
    };
}
