package ir.webplex.android.automation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import ir.webplex.android.api.ServiceBuilder;
import ir.webplex.android.api.requests.SubmitReceiptRequest;
import ir.webplex.android.api.responses.SubmitReceiptResponse;
import ir.webplex.android.api.utils.CancelableCallback;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ActivityBase;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.automation.models.ReceptorItem;
import ir.webplex.android.automation.ui.CustomTextView;
import ir.webplex.android.automation.ui.DrawingView;
import ir.webplex.android.automation.ui.OnDrawingListener;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.IntentUtils;
import ir.webplex.android.core.helpers.MessagingUitls;
import ir.webplex.android.core.logging.Logger;

public class SubmitReceiptActivity extends ActivityBase implements View.OnClickListener, OnDrawingListener, View.OnTouchListener {
    public static final String TAG = SubmitReceiptActivity.class.getSimpleName();

    private ActionBar mActionBar;
    private MenuItem mShowDetails;
    private MenuItem mShowReceivers;
    private MenuItem mSubmitReceipt;
    private LinearLayout mForm;
    private TextView mLetterLabel;
    private EditText mLetterValue;
    private ImageButton mLetterSelect;
    private ImageButton mLetterClear;
    private TextView mReceptorLabel;
    private EditText mReceptorValue;
    private ImageButton mReceptorSelect;
    private ImageButton mReceptorClear;
    private TextView mSignatureValue;
    private Animation mSignatureValueAnimation;
    private DrawingView mSignatureSurface;
    private ImageButton mSignatureClear;
    private RelativeLayout mProgressWrapper;
    private RelativeLayout mProgressMessageWrapper;
    private CustomTextView mProgressMessage;

    private LetterItem mSelectedLetterInstance;
    private ReceptorItem mSelectedReceptorInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        setContentView(R.layout.activity_submit_receipt);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mForm = (LinearLayout) findViewById(R.id.submit_receipt_form);
        mLetterLabel = (TextView) findViewById(R.id.submit_receipt_letter_label);
        mLetterValue = (EditText) findViewById(R.id.submit_receipt_letter_value);
        mLetterSelect = (ImageButton) findViewById(R.id.submit_receipt_letter_select);
        mLetterClear = (ImageButton) findViewById(R.id.submit_receipt_letter_clear);
        mReceptorLabel = (TextView) findViewById(R.id.submit_receipt_receptor_label);
        mReceptorValue = (EditText) findViewById(R.id.submit_receipt_receptor_value);
        mReceptorSelect = (ImageButton) findViewById(R.id.submit_receipt_receptor_select);
        mReceptorClear = (ImageButton) findViewById(R.id.submit_receipt_receptor_clear);
        mSignatureValue = (TextView) findViewById(R.id.submit_receipt_signature_value);
        mSignatureValueAnimation = AnimationUtils.loadAnimation(this, R.anim.submit_receipt_signature_value);
        mSignatureSurface = (DrawingView) findViewById(R.id.submit_receipt_signature_surface);
        mSignatureClear = (ImageButton) findViewById(R.id.submit_receipt_signature_clear);
        mProgressWrapper = (RelativeLayout) findViewById(R.id.submit_receipt_progress_wrapper);
        mProgressMessageWrapper = (RelativeLayout) findViewById(R.id.submit_receipt_progress_message_wrapper);
        mProgressMessage = (CustomTextView) findViewById(R.id.submit_receipt_progress_message);
        Logger.onViewLookedUp(TAG);

        mLetterSelect.setOnClickListener(this);
        mLetterClear.setOnClickListener(this);
        mReceptorSelect.setOnClickListener(this);
        mReceptorClear.setOnClickListener(this);
        mSignatureValue.setOnTouchListener(this);
        mSignatureSurface.setOnDrawingListener(this);
        mSignatureClear.setOnClickListener(this);

        Logger.onExited(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_submit_receipt, menu);
        mShowDetails = menu.findItem(R.id.bar_submit_receipt_show_details);
        mShowReceivers = menu.findItem(R.id.bar_submit_receipt_show_receivers);
        mSubmitReceipt = menu.findItem(R.id.bar_submit_receipt_submit_receipt);

        syncViewsState();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_submit_receipt_show_details:
                IntentUtils.goToDetails(SubmitReceiptActivity.this, mSelectedLetterInstance);
                return true;

            case R.id.bar_submit_receipt_show_receivers:
                IntentUtils.goToLetterReceivers(SubmitReceiptActivity.this, mSelectedLetterInstance);
                return true;

            case R.id.bar_submit_receipt_submit_receipt:
                onSubmittingReceipt();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SELECT_LETTER:
                if (resultCode == RESULT_OK)
                    mSelectedLetterInstance = IntentUtils.getSelectedLetter(data);
                break;

            case INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SELECT_RECEPTOR:
                if (resultCode == RESULT_OK)
                    mSelectedReceptorInstance = IntentUtils.getSelectedReceptor(data);


                break;
        }

        syncViewsState();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_receipt_letter_select:
                IntentUtils.goToLetters(SubmitReceiptActivity.this);
                return;

            case R.id.submit_receipt_letter_clear:
                mSelectedLetterInstance = null;
                mSelectedReceptorInstance = null;
                break;

            case R.id.submit_receipt_receptor_select:
                IntentUtils.goToReceptors(SubmitReceiptActivity.this, mSelectedLetterInstance);
                return;

            case R.id.submit_receipt_receptor_clear:
                mSelectedReceptorInstance = null;
                break;

            case R.id.submit_receipt_signature_clear:
                mSignatureSurface.clearSurface();
                break;
        }

        syncViewsState();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mSignatureSurface.onTouchEvent(event);
        return false;
    }

    @Override
    public void onDrawing(DrawingView v) {
        syncViewsState();
    }

    private void onSubmittingReceipt() {
        CancelableCallback.cancelAll(TAG);

        mShowDetails.setEnabled(false);
        mShowReceivers.setEnabled(false);
        mSubmitReceipt.setEnabled(false);
        mForm.setVisibility(View.INVISIBLE);
        mProgressWrapper.setVisibility(View.VISIBLE);
        mProgressMessageWrapper.setVisibility(View.INVISIBLE);

        SubmitReceiptRequest request = null;
        try {
            request = new SubmitReceiptRequest(
                    SessionManager.getInstance().getToken(),
                    mSelectedLetterInstance.getLetterGuid(),
                    mSelectedReceptorInstance.getPostId(),
                    mSignatureSurface.exportSurface()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.onSubmittingReceipt(TAG, request);

        ServiceBuilder
                .getInstance()
                .getSearchService()
                .submitReceipt(request)
                .enqueue(new SubmitReceiptResponseCallback(TAG));
    }

    private class SubmitReceiptResponseCallback extends CancelableCallback<SubmitReceiptResponse> {
        public SubmitReceiptResponseCallback(String tag) {
            super(tag);
        }

        @Override
        public void onSuccess(SubmitReceiptResponse result) {
            Logger.onSubmitReceiptResponse(TAG, result);

            mShowDetails.setEnabled(true);
            mShowReceivers.setEnabled(true);
            mSubmitReceipt.setEnabled(true);
            mForm.setVisibility(View.VISIBLE);
            mProgressWrapper.setVisibility(View.INVISIBLE);

            if (result.isReceipted()) {
                mSelectedReceptorInstance = null;
                mSignatureSurface.clearSurface();
                syncViewsState();
                MessagingUitls.showToast(SubmitReceiptActivity.this, R.string.submit_receipt_info_submit_succeeded);
            } else if (result.hasReception())
                MessagingUitls.showToast(SubmitReceiptActivity.this, R.string.submit_receipt_error_already_receipted, result.getReceptionRelativeDate());
            else
                MessagingUitls.showToast(SubmitReceiptActivity.this, R.string.submit_receipt_error_submit_failed);
        }

        @Override
        public void onError(Throwable error) {
            Logger.onError(TAG, error);

            mForm.setVisibility(View.INVISIBLE);
            mProgressWrapper.setVisibility(View.INVISIBLE);
            mProgressMessageWrapper.setVisibility(View.VISIBLE);
            mProgressMessage.setText(R.string.apis_error_invalid_response);
        }
    }

    private void syncViewsState() {
        mLetterLabel.setVisibility(mSelectedLetterInstance == null ? View.GONE : View.VISIBLE);
        mLetterValue.setText(mSelectedLetterInstance == null ? null : mSelectedLetterInstance.getSubject());
        mLetterSelect.setVisibility(mSelectedLetterInstance == null ? View.VISIBLE : View.GONE);
        mLetterClear.setVisibility(mSelectedLetterInstance == null ? View.GONE : View.VISIBLE);

        mReceptorLabel.setVisibility(mSelectedReceptorInstance == null ? View.GONE : View.VISIBLE);
        mReceptorValue.setText(mSelectedReceptorInstance == null ? null : mSelectedReceptorInstance.getTitle());
        mReceptorSelect.setVisibility(mSelectedReceptorInstance == null ? View.VISIBLE : View.GONE);
        mReceptorSelect.setEnabled(mSelectedLetterInstance != null);
        mReceptorClear.setVisibility(mSelectedReceptorInstance == null ? View.GONE : View.VISIBLE);

        mSignatureValue.setVisibility(mSignatureSurface.isSurfaceDirty() ? View.GONE : View.VISIBLE);
        if (mSignatureValue.getVisibility() == View.VISIBLE) {
            mSignatureValue.setAnimation(mSignatureValueAnimation);
            mSignatureValueAnimation.reset();
            mSignatureValueAnimation.start();
        } else
            mSignatureValue.clearAnimation();
        mSignatureClear.setEnabled(mSignatureSurface.isSurfaceDirty());

        mShowDetails.setEnabled(mSelectedLetterInstance != null);
        mShowReceivers.setEnabled(mSelectedLetterInstance != null);
        mSubmitReceipt.setEnabled(mSelectedLetterInstance != null && mSelectedReceptorInstance != null && mSignatureSurface.isSurfaceDirty());
    }
}
