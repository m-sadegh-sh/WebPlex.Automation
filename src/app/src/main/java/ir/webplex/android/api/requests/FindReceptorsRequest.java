package ir.webplex.android.api.requests;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class FindReceptorsRequest {
    public static final String TAG = FindReceptorsRequest.class.getSimpleName();

    @SerializedName("Token")
    private String mToken;

    @SerializedName("LetterGuid")
    private UUID mLetterGuid;

    @SerializedName("Query")
    private String mQuery;

    @SerializedName("PageIndex")
    private int mPageIndex;

    @SerializedName("PageSize")
    private int mPageSize;

    public FindReceptorsRequest(String token, UUID letterGuid, String query, int pageIndex, int pageSize) {
        mToken = token;
        mLetterGuid = letterGuid;
        mQuery = query;
        mPageIndex = pageIndex;
        mPageSize = pageSize;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Token: %s, LetterGuid: %s, Query: %s, PageIndex: %s, PageSize: %s }",
                TAG,
                mToken,
                mLetterGuid,
                mQuery,
                mPageIndex,
                mPageSize
        );
    }
}
