package ir.webplex.android.automation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ir.webplex.android.api.ServiceBuilder;
import ir.webplex.android.api.requests.FindDetailsRequest;
import ir.webplex.android.api.responses.FindDetailsResponse;
import ir.webplex.android.api.utils.CancelableCallback;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ActivityBase;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.automation.ui.CustomTextView;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.IntentUtils;
import ir.webplex.android.core.logging.Logger;

public class DetailsActivity extends ActivityBase {
    public static final String TAG = DetailsActivity.class.getSimpleName();

    private ActionBar mActionBar;
    private LinearLayout mForm;
    private EditText mSubjectValue;
    private EditText mIndicatorDateValue;
    private EditText mIndicatorNumberValue;
    private EditText mSenderNameValue;
    private EditText mLetterTypeValue;
    private EditText mPrivacyValue;
    private EditText mHasAttachmentsValue;
    private RelativeLayout mProgressWrapper;
    private RelativeLayout mProgressMessageWrapper;
    private CustomTextView mProgressMessage;

    private LetterItem mSelectedLetterInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        setContentView(R.layout.activity_details);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mForm = (LinearLayout) findViewById(R.id.details_form);
        mSubjectValue = (EditText) findViewById(R.id.details_subject_value);
        mIndicatorDateValue = (EditText) findViewById(R.id.details_indicator_date_value);
        mIndicatorNumberValue = (EditText) findViewById(R.id.details_indicator_number_value);
        mSenderNameValue = (EditText) findViewById(R.id.details_sender_name_value);
        mLetterTypeValue = (EditText) findViewById(R.id.details_letter_type_value);
        mPrivacyValue = (EditText) findViewById(R.id.details_privacy_value);
        mHasAttachmentsValue = (EditText) findViewById(R.id.details_has_attachments_value);
        mProgressWrapper = (RelativeLayout) findViewById(R.id.details_progress_wrapper);
        mProgressMessageWrapper = (RelativeLayout) findViewById(R.id.details_progress_message_wrapper);
        mProgressMessage = (CustomTextView) findViewById(R.id.details_progress_message);
        Logger.onViewLookedUp(TAG);

        mSelectedLetterInstance = IntentUtils.getSelectedLetter(getIntent());

        onFindingDetails();

        Logger.onExited(TAG, "onCreate");
    }

    private void onFindingDetails() {
        CancelableCallback.cancelAll(TAG);

        mForm.setVisibility(View.INVISIBLE);
        mProgressWrapper.setVisibility(View.VISIBLE);
        mProgressMessageWrapper.setVisibility(View.INVISIBLE);

        FindDetailsRequest request = new FindDetailsRequest(
                SessionManager.getInstance().getToken(),
                mSelectedLetterInstance.getLetterGuid()
        );
        Logger.onFindingDetails(TAG, request);

        ServiceBuilder
                .getInstance()
                .getSearchService()
                .findDetails(request)
                .enqueue(new FindDetailsResponseCallback(TAG));
    }

    private class FindDetailsResponseCallback extends CancelableCallback<FindDetailsResponse> {
        public FindDetailsResponseCallback(String tag) {
            super(tag);
        }

        @Override
        public void onSuccess(FindDetailsResponse result) {
            Logger.onFindDetailsResponse(TAG, result);

            mForm.setVisibility(View.VISIBLE);
            mProgressWrapper.setVisibility(View.INVISIBLE);

            mSubjectValue.setText(result.getSubject());
            mIndicatorDateValue.setText(result.getIndicatorDate());
            mIndicatorNumberValue.setText(result.getIndicatorNumber());
            mSenderNameValue.setText(result.getSenderName());
            mLetterTypeValue.setText(result.getLetterType());
            mPrivacyValue.setText(result.getPrivacy());
            mHasAttachmentsValue.setText(getResources().getString(result.hasAttachments() ? R.string.details_has_attachments_true : R.string.details_has_attachments_false));
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
}
