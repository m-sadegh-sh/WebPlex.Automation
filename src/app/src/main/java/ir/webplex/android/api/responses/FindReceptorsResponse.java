package ir.webplex.android.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FindReceptorsResponse {
    public static final String TAG = FindReceptorsResponse.class.getSimpleName();

    @SerializedName("Count")
    private int mCount;

    @SerializedName("HasMorePage")
    private boolean mHasMorePage;

    @SerializedName("Items")
    private ArrayList<FindReceptorItemResponse> mItems;

    public int getCount() {
        return mCount;
    }

    public boolean hasMorePage() {
        return mHasMorePage;
    }

    public ArrayList<FindReceptorItemResponse> getItems() {
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
