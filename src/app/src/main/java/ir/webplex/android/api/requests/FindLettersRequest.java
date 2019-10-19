package ir.webplex.android.api.requests;

import com.google.gson.annotations.SerializedName;

import ir.webplex.android.api.parameters.LetterType;
import ir.webplex.android.api.parameters.ReceptionType;

public class FindLettersRequest {
    public static final String TAG = FindLettersRequest.class.getSimpleName();

    @SerializedName("Token")
    private String mToken;

    @SerializedName("Query")
    private String mQuery;

    @SerializedName("LetterType")
    private LetterType mLetterType;

    @SerializedName("ReceptionType")
    private ReceptionType mReceptionType;

    @SerializedName("PageIndex")
    private int mPageIndex;

    @SerializedName("PageSize")
    private int mPageSize;

    public FindLettersRequest(String token, String query, LetterType letterType, ReceptionType receptionType, int pageIndex, int pageSize) {
        mToken = token;
        mQuery = query;
        mLetterType = letterType;
        mReceptionType = receptionType;
        mPageIndex = pageIndex;
        mPageSize = pageSize;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Token: %s, Query: %s, LetterType: %s, ReceptionType: %s, PageIndex: %s, PageSize: %s }",
                TAG,
                mToken,
                mQuery,
                mLetterType,
                mReceptionType,
                mPageIndex,
                mPageSize
        );
    }
}
