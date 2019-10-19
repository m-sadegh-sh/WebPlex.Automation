package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

public class FindDetailsResponse {
    public static final String TAG = FindDetailsResponse.class.getSimpleName();

    @SerializedName("Subject")
    private String mSubject;

    @SerializedName("IndicatorDate")
    private String mIndicatorDate;

    @SerializedName("IndicatorNumber")
    private String mIndicatorNumber;

    @SerializedName("SenderName")
    private String mSenderName;

    @SerializedName("LetterType")
    private String mLetterType;

    @SerializedName("Privacy")
    private String mPrivacy;

    @SerializedName("HasAttachments")
    private boolean mHasAttachments;

    @SerializedName("ReceiversCount")
    private int mReceiversCount;

    public String getSubject() {
        return mSubject;
    }

    public String getIndicatorDate() {
        return mIndicatorDate;
    }

    public String getIndicatorNumber() {
        return mIndicatorNumber;
    }

    public String getSenderName() {
        return mSenderName;
    }

    public String getLetterType() {
        return mLetterType;
    }

    public String getPrivacy() {
        return mPrivacy;
    }

    public boolean hasAttachments() {
        return mHasAttachments;
    }

    public int getReceiversCount() {
        return mReceiversCount;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Subject: %s, IndicatorDate: %s, IndicatorNumber: %s, SenderName: %s, LetterType: %s, Privacy: %s, HasAttachments: %s, ReceiversCount: %s }",
                TAG,
                mSubject,
                mIndicatorDate,
                mIndicatorNumber,
                mSenderName,
                mLetterType,
                mPrivacy,
                mHasAttachments,
                mReceiversCount
        );
    }
}
