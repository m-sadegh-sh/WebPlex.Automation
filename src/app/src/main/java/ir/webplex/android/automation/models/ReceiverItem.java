package ir.webplex.android.automation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReceiverItem implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public ReceiverItem createFromParcel(Parcel in) {
            return new ReceiverItem(in);
        }

        public ReceiverItem[] newArray(int size) {
            return new ReceiverItem[size];
        }
    };

    private String mTitle;
    private String mEmail;
    private String mRelativeDate;

    public ReceiverItem(String title, String email, String relativeDate) {
        mTitle = title;
        mEmail = email;
        mRelativeDate = relativeDate;
    }

    private ReceiverItem(Parcel in) {
        mTitle = in.readString();
        mEmail = in.readString();
        mRelativeDate = in.readString();
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mEmail);
        out.writeString(mRelativeDate);
    }
}
