package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

public class FindReceptorItemResponse {
    public static final String TAG = FindReceptorItemResponse.class.getSimpleName();

    @SerializedName("PostId")
    private int mPostId;

    @SerializedName("Title")
    private String mTitle;

    @SerializedName("Email")
    private String mEmail;

    public int getPostId() {
        return mPostId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEmail() {
        return mEmail;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { PostId: %s, Title: %s, Email: %s }",
                TAG,
                mPostId,
                mTitle,
                mEmail
        );
    }
}
