package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FindLettersResponse {
    public static final String TAG = FindLettersResponse.class.getSimpleName();

    @SerializedName("Count")
    private int mCount;

    @SerializedName("HasMorePage")
    private boolean mHasMorePage;

    @SerializedName("Items")
    private ArrayList<FindLetterItemResponse> mItems;

    public int getCount() {
        return mCount;
    }

    public boolean hasMorePage() {
        return mHasMorePage;
    }

    public ArrayList<FindLetterItemResponse> getItems() {
        return mItems;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Count: %s, HasMorePage: %s, Items.First: %s }",
                TAG,
                mCount,
                mHasMorePage,
                mCount > 0 ? mItems.get(0).toString() : "(null)"
        );
    }
}
