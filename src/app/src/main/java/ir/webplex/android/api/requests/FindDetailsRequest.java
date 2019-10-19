package ir.webplex.android.api.requests;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class FindDetailsRequest {
    public static final String TAG = FindDetailsRequest.class.getSimpleName();

    @SerializedName("Token")
    private String mToken;

    @SerializedName("LetterGuid")
    private UUID mLetterGuid;

    public FindDetailsRequest(String token, UUID letterGuid) {
        mToken = token;
        mLetterGuid = letterGuid;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Token: %s, LetterGuid: %s }",
                TAG,
                mToken,
                mLetterGuid
        );
    }
}
