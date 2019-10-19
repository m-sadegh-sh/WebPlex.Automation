package ir.webplex.android.automation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.webplex.android.api.ServiceBuilder;
import ir.webplex.android.api.parameters.ReceptionType;
import ir.webplex.android.api.requests.FindLettersRequest;
import ir.webplex.android.api.responses.FindLettersResponse;
import ir.webplex.android.api.utils.CancelableCallback;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ListActivityBase;
import ir.webplex.android.automation.adapters.InfiniteScrollListener;
import ir.webplex.android.automation.adapters.LettersAdapter;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.automation.ui.CustomTextView;
import ir.webplex.android.automation.ui.CustomTextWatcher;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.IntentUtils;
import ir.webplex.android.core.helpers.MappingUtils;
import ir.webplex.android.core.helpers.ViewUtils;
import ir.webplex.android.core.logging.Logger;

public class LettersActivity extends ListActivityBase {
    public static final String TAG = LettersActivity.class.getSimpleName();

    private ActionBar mActionBar;
    private EditText mQuery;
    private Menu mMenu;
    private MenuItem mClear;
    private RelativeLayout mProgressWrapper;
    private RelativeLayout mProgressMessageWrapper;
    private CustomTextView mProgressMessage;

    private ReceptionType mReceptionType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        setContentView(R.layout.activity_letters);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.bar_letters);

        mQuery = (EditText) mActionBar.getCustomView().findViewById(R.id.bar_letters_query);
        mProgressWrapper = (RelativeLayout) findViewById(R.id.letters_progress_wrapper);
        mProgressMessageWrapper = (RelativeLayout) findViewById(R.id.letters_progress_message_wrapper);
        mProgressMessage = (CustomTextView) findViewById(R.id.letters_progress_message);
        Logger.onViewLookedUp(TAG);

        mQuery.addTextChangedListener(QueryTextChangedListener);
        mQuery.requestFocus();

        getListView().setOnScrollListener(OnScrollListener);

        onFindingLetters(true);

        Logger.onExited(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_letters, menu);
        mMenu = menu;
        mClear = menu.findItem(R.id.bar_letters_clear);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ViewUtils.clearCheckedState(mMenu);

        switch (item.getItemId()) {
            case R.id.bar_letters_clear:
                if (!TextUtils.isEmpty(mQuery.getText()))
                    mQuery.setText(null);

                return true;

            case R.id.bar_letters_reception_types_all:
                mReceptionType = null;
                break;

            case R.id.bar_letters_reception_types_received:
                mReceptionType = ReceptionType.RECEIVED;
                break;

            case R.id.bar_letters_reception_types_not_received:
                mReceptionType = ReceptionType.NOT_RECEIVED;
                break;
        }

        switch (item.getItemId()) {
            case R.id.bar_letters_reception_types_all:
            case R.id.bar_letters_reception_types_received:
            case R.id.bar_letters_reception_types_not_received:
                item.setChecked(true);
                onFindingLetters(true);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        LetterItem item = (LetterItem) getListAdapter().getItem(position);

        IntentUtils.putSelectedLetter(
                LettersActivity.this,
                item
        );
    }

    private void onFindingLetters(boolean fromBeginning) {
        CancelableCallback.cancelAll(TAG);

        if (fromBeginning) {
            LettersAdapter adapter = ((LettersAdapter) getListAdapter());
            if (adapter != null)
                adapter.clearItems();

            OnScrollListener.reset();
        }

        mProgressMessageWrapper.setVisibility(View.INVISIBLE);
        mProgressWrapper.setVisibility(View.VISIBLE);

        String query = String.valueOf(mQuery.getText());
        int pageIndex = OnScrollListener.getCurrentPageIndex() + 1;
        int pageSize = OnScrollListener.getPageSize();

        FindLettersRequest request = new FindLettersRequest(
                SessionManager.getInstance().getToken(),
                query,
                API_PARAMETERS_SEARCH_FIND_LETTERS_LETTER_TYPES,
                mReceptionType,
                pageIndex,
                pageSize
        );
        Logger.onFindingLetters(TAG, request);

        ServiceBuilder
                .getInstance()
                .getSearchService()
                .findLetters(request)
                .enqueue(new FindLettersResponseCallback(TAG));
    }

    private CustomTextWatcher QueryTextChangedListener = new CustomTextWatcher() {
        @Override
        public void afterTextChanged(String text) {
            mClear.setEnabled(!TextUtils.isEmpty(text));
            onFindingLetters(true);
        }
    };

    private class FindLettersResponseCallback extends CancelableCallback<FindLettersResponse> {
        public FindLettersResponseCallback(String tag) {
            super(tag);
        }

        @Override
        public void onSuccess(FindLettersResponse result) {
            Logger.onFindLettersResponse(TAG, result);

            mProgressWrapper.setVisibility(View.INVISIBLE);

            if (result.getCount() > 0)
                MappingUtils.updateAdapter(LettersActivity.this, result);
            else if (OnScrollListener.isInFirstPage()) {
                mProgressMessageWrapper.setVisibility(View.VISIBLE);
                mProgressMessage.setText(R.string.letters_error_no_results);
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

    private InfiniteScrollListener OnScrollListener = new InfiniteScrollListener(API_PARAMETERS_SEARCH_FIND_LETTERS_PAGE_SIZE) {
        @Override
        public void onLoadNextPage() {
            onFindingLetters(false);
        }
    };
}
