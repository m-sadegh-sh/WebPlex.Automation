package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

public class FindReceiverItemResponse {
    public static final String TAG = FindReceiverItemResponse.class.getSimpleName();

    @SerializedName("Title")
    private String mTitle;

    @SerializedName("Email")
    private String mEmail;

    @SerializedName("RelativeDate")
    private String mRelativeDate;

    public String getTitle() {
        return mTitle;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getRelativeDate() {
        return mRelativeDate;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Title: %s, Email: %s, RelativeDate: %s }",
                TAG,
                mTitle,
                mEmail,
                mRelativeDate
        );
    }
}
