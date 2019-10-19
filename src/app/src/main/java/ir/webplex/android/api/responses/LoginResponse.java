package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;

import ir.webplex.android.api.parameters.Permission;

public class LoginResponse {
    public static final String TAG = LoginResponse.class.getSimpleName();

    @SerializedName("Token")
    private String mToken;

    @SerializedName("Reason")
    private String mReason;

    @SerializedName("Title")
    private String mTitle;

    @SerializedName("Email")
    private String mEmail;

    @SerializedName("Avatar")
    private String mAvatar;

    @SerializedName("Permissions")
    public HashSet<Permission> mPermissions;

    public String getToken() {
        return mToken;
    }

    public String getReason() {
        return mReason;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public HashSet<Permission> getPermission() {
        return mPermissions;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Token: %s, Reason: %s, Title: %s, Email: %s, Avatar: %s, Permissions: %s }",
                TAG,
                mToken,
                mReason,
                mTitle,
                mEmail,
                mAvatar,
                mPermissions
        );
    }
}
