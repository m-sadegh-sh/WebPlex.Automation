package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FindReceiversResponse {
    public static final String TAG = FindReceiversResponse.class.getSimpleName();

    @SerializedName("Count")
    private int mCount;

    @SerializedName("HasMorePage")
    private boolean mHasMorePage;

    @SerializedName("Items")
    private ArrayList<FindReceiverItemResponse> mItems;

    public int getCount() {
        return mCount;
    }

    public boolean hasMorePage() {
        return mHasMorePage;
    }

    public ArrayList<FindReceiverItemResponse> getItems() {
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
