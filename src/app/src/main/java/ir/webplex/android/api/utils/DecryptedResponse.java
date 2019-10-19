package ir.webplex.android.api.utils;

import java.io.IOException;

import ir.webplex.android.core.helpers.SecurityHelpers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DecryptedResponse {
    private Response mInnerResponse;

    public DecryptedResponse(Response innerResponse) {
        mInnerResponse = innerResponse;
    }

    public Response build() {
        Response.Builder responseBuilder = mInnerResponse.newBuilder();
        ResponseBody innerResponseBody = mInnerResponse.body();

        String encryptedResponse = null;
        try {
            encryptedResponse = innerResponseBody.string();
        } catch (IOException e) {
        }
        String plainResponse = SecurityHelpers.decrypt(encryptedResponse);

        ResponseBody responseBody = ResponseBody.create(
                innerResponseBody.contentType(),
                plainResponse
        );

        return responseBuilder
                .body(responseBody)
                .build();
    }
}