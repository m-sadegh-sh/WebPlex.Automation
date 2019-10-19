package ir.webplex.android.automation.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.webplex.android.api.ServiceBuilder;
import ir.webplex.android.api.requests.LoginRequest;
import ir.webplex.android.api.responses.LoginResponse;
import ir.webplex.android.api.utils.CancelableCallback;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.activities.abstraction.ActivityBase;
import ir.webplex.android.automation.ui.CustomTextWatcher;
import ir.webplex.android.core.SessionManager;
import ir.webplex.android.core.helpers.IntentUtils;
import ir.webplex.android.core.helpers.MessagingUitls;
import ir.webplex.android.core.helpers.ViewUtils;
import ir.webplex.android.core.logging.Logger;

public class LoginActivity extends ActivityBase implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    private MenuItem mShowBaseUri;
    private MenuItem mSignIn;
    private LinearLayout mForm;
    private RelativeLayout mBaseUriRow;
    private EditText mBaseUri;
    private ImageView mBaseUriReset;
    private EditText mEmail;
    private EditText mPassword;
    private RelativeLayout mProgressWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.onEntering(TAG, "onCreate");

        if (!TextUtils.isEmpty(SessionManager.getInstance().getToken())) {
            Logger.onAlreadyLoggedIn(TAG);
            IntentUtils.goToMain(LoginActivity.this);
            return;
        }

        ViewUtils.updateActionBarIcon(this);
        setContentView(R.layout.activity_login);

        mForm = (LinearLayout) findViewById(R.id.login_form);
        mBaseUriRow = (RelativeLayout) findViewById(R.id.login_base_uri_row);
        mBaseUri = (EditText) findViewById(R.id.login_base_uri);
        mBaseUriReset = (ImageView) findViewById(R.id.login_base_uri_reset);
        mEmail = (EditText) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_password);
        mProgressWrapper = (RelativeLayout) findViewById(R.id.login_progress_wrapper);
        Logger.onViewLookedUp(TAG);


        mBaseUri.setText(SessionManager.getInstance().getBaseUri());
        mBaseUri.addTextChangedListener(BaseUriTextChangedListener);
        mBaseUriReset.setOnClickListener(this);
        mBaseUriReset.setVisibility(TextUtils.equals(mBaseUri.getText(), API_URIS_BASE) ? View.GONE : View.VISIBLE);

        if (TextUtils.isEmpty(SessionManager.getInstance().getEmail()))
            mEmail.requestFocus();
        else {
            mEmail.setText(SessionManager.getInstance().getEmail());
            mPassword.requestFocus();
        }

        Logger.onExited(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isFinishing())
            return false;

        getMenuInflater().inflate(R.menu.bar_login, menu);
        mShowBaseUri = menu.findItem(R.id.bar_login_show_base_uri);
        mSignIn = menu.findItem(R.id.bar_login_sign_in);
        ViewUtils.disableOnEmpty(mSignIn, mEmail, mPassword);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_login_show_base_uri:
                if (mBaseUriRow.getVisibility() == View.VISIBLE)
                    mBaseUriRow.setVisibility(View.GONE);
                else {
                    mBaseUriRow.setVisibility(View.VISIBLE);
                    mBaseUri.requestFocus();
                }
                return true;

            case R.id.bar_login_sign_in:
                String email = String.valueOf(mEmail.getText());
                String password = String.valueOf(mPassword.getText());

                onValidateCredentials(email, password);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private CustomTextWatcher BaseUriTextChangedListener = new CustomTextWatcher() {
        @Override
        public void afterTextChanged(String text) {
            mBaseUriReset.setVisibility(TextUtils.equals(text, API_URIS_BASE) ? View.GONE : View.VISIBLE);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_base_uri_reset:
                mBaseUri.setText(API_URIS_BASE);
                mBaseUri.setSelection(mBaseUri.getText().length());
                break;
        }
    }

    private void onValidateCredentials(final String email, final String password) {
        CancelableCallback.cancelAll(TAG);

        MessagingUitls.hideLatestToast();
        MessagingUitls.hideSoftInput(mEmail.getWindowToken());

        mShowBaseUri.setEnabled(false);
        mSignIn.setEnabled(false);
        mForm.setVisibility(View.INVISIBLE);

        if (mBaseUriRow.getVisibility() == View.VISIBLE)
            SessionManager.getInstance().setBaseUri(String.valueOf(mBaseUri.getText()), false);

        mProgressWrapper.setVisibility(View.VISIBLE);

        LoginRequest request = new LoginRequest(email, password);
        Logger.onLoggingIn(TAG, request);

        ServiceBuilder
                .getInstance()
                .getAuthenticateService()
                .login(request)
                .enqueue(new LoginResponseCallback(TAG));
    }

    private class LoginResponseCallback extends CancelableCallback<LoginResponse> {
        public LoginResponseCallback(String tag) {
            super(tag);
        }

        @Override
        public void onSuccess(LoginResponse result) {
            Logger.onLoginResponse(TAG, result);

            SessionManager.getInstance().applyNonPersisted();

            if (!TextUtils.isEmpty(result.getToken())) {
                SessionManager.getInstance().signIn(result);
                IntentUtils.goToMain(LoginActivity.this);

                return;
            }

            mShowBaseUri.setEnabled(true);
            mSignIn.setEnabled(true);
            mForm.setVisibility(View.VISIBLE);
            mProgressWrapper.setVisibility(View.INVISIBLE);

            if (!TextUtils.isEmpty(result.getReason()))
                MessagingUitls.showToast(LoginActivity.this, result.getReason());
            else
                MessagingUitls.showToast(LoginActivity.this, R.string.login_error_invalid_credentials);
        }

        @Override
        public void onError(Throwable error) {
            Logger.onError(TAG, error);

            mShowBaseUri.setEnabled(true);
            mSignIn.setEnabled(true);
            mForm.setVisibility(View.VISIBLE);
            mBaseUriRow.setVisibility(View.VISIBLE);
            mProgressWrapper.setVisibility(View.INVISIBLE);

            MessagingUitls.showToast(LoginActivity.this, R.string.apis_error_invalid_response);
        }
    }
}
