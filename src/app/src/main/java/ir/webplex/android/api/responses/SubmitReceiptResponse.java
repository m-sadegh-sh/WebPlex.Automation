package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

public class SubmitReceiptResponse {
    public static final String TAG = SubmitReceiptResponse.class.getSimpleName();

    @SerializedName("Receipted")
    private boolean mReceipted;

    @SerializedName("HasReception")
    private boolean mHasReception;

    @SerializedName("ReceptionRelativeDate")
    private String mReceptionRelativeDate;

    public boolean isReceipted() {
        return mReceipted;
    }

    public boolean hasReception() {
        return mHasReception;
    }

    public String getReceptionRelativeDate() {
        return mReceptionRelativeDate;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Receipted: %s, HasReception: %s, ReceptionRelativeDate: %s }",
                TAG,
                mReceipted,
                mHasReception,
                mReceptionRelativeDate
        );
    }
}
