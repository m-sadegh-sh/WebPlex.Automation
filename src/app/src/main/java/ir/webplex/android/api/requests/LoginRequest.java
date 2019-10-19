package ir.webplex.android.api.requests;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    public static final String TAG = LoginRequest.class.getSimpleName();

    @SerializedName("Email")
    private String mEmail;

    @SerializedName("Password")
    private String mPassword;

    public LoginRequest(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Email: %s, Password: %s }",
                TAG,
                mEmail,
                mPassword
        );
    }
}
