package ir.webplex.android.automation.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReceptorItem implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public ReceptorItem createFromParcel(Parcel in) {
            return new ReceptorItem(in);
        }

        public ReceptorItem[] newArray(int size) {
            return new ReceptorItem[size];
        }
    };

    private int mPostId;
    private String mTitle;
    private String mEmail;

    public ReceptorItem(int postId, String title, String email) {
        mPostId = postId;
        mTitle = title;
        mEmail = email;
    }

    private ReceptorItem(Parcel in) {
        mPostId = in.readInt();
        mTitle = in.readString();
        mEmail = in.readString();
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mPostId);
        out.writeString(mTitle);
        out.writeString(mEmail);
    }
}
