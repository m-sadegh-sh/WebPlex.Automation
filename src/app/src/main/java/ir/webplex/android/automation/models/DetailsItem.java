package ir.webplex.android.automation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class DetailsItem implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public DetailsItem createFromParcel(Parcel in) {
            return new DetailsItem(in);
        }

        public DetailsItem[] newArray(int size) {
            return new DetailsItem[size];
        }
    };

    private UUID mLetterGuid;
    private String mSubject;
    private String mIndicatorDate;
    private String mIndicatorNumber;
    private int mReceiversCount;

    public DetailsItem(UUID letterGuid, String subject, String indicatorDate, String indicatorNumber, int receiversCount) {
        mLetterGuid = letterGuid;
        mSubject = subject;
        mIndicatorDate = indicatorDate;
        mIndicatorNumber = indicatorNumber;
        mReceiversCount = receiversCount;
    }

    private DetailsItem(Parcel in) {
        mLetterGuid = UUID.fromString(in.readString());
        mSubject = in.readString();
        mIndicatorDate = in.readString();
        mIndicatorNumber = in.readString();
        mReceiversCount = in.readInt();
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mLetterGuid.toString());
        out.writeString(mSubject);
        out.writeString(mIndicatorDate);
        out.writeString(mIndicatorNumber);
        out.writeInt(mReceiversCount);
    }
}
