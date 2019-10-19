package ir.webplex.android.api.requests;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class SubmitReceiptRequest {
    public static final String TAG = SubmitReceiptRequest.class.getSimpleName();

    @SerializedName("Token")
    private String mToken;

    @SerializedName("LetterGuid")
    private UUID mLetterGuid;

    @SerializedName("PostId")
    private int mPostId;

    @SerializedName("CapturedSignature")
    private String mCapturedSignature;

    public SubmitReceiptRequest(String token, UUID letterGuid, int postId, String capturedSignature) {
        mCapturedSignature = capturedSignature;
        mToken = token;
        mLetterGuid = letterGuid;
        mPostId = postId;
    }

    @Override
    public String toString() {
        return String.format(
                "%s { Token: %s, LetterGuid: %s, PostId: %s, CapturedSignature: %s }",
                TAG,
                mToken,
                mLetterGuid,
                mPostId,
                mCapturedSignature
        );
    }
}
