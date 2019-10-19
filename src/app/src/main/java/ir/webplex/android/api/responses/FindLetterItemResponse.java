package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class FindLetterItemResponse {
    public static final String TAG = FindLetterItemResponse.class.getSimpleName();

    @SerializedName("LetterGuid")
    private UUID mLetterGuid;

    @SerializedName("Subject")
    private String mSubject;

    @SerializedName("IndicatorDate")
    private String mIndicatorDate;

    @SerializedName("IndicatorNumber")
    private String mIndicatorNumber;

    @SerializedName("ReceiversCount")
    private int mReceiversCount;

    public UUID getLetterGuid() {
        return mLetterGuid;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getIndicatorDate() {
        return mIndicatorDate;
    }

    public String getIndicatorNumber() {
        return mIndicatorNumber;
    }

    public int getReceiversCount() {
        return mReceiversCount;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { LetterGuid: %s, Subject: %s, IndicatorDate: %s, IndicatorNumber: %s, ReceiversCount: %s }",
                TAG,
                mLetterGuid,
                mSubject,
                mIndicatorDate,
                mIndicatorNumber,
                mReceiversCount
        );
    }
}
