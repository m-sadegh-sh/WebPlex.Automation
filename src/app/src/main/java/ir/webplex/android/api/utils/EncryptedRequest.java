package ir.webplex.android.api.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import ir.webplex.android.core.Constants;
import ir.webplex.android.core.helpers.SecurityHelpers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

public class EncryptedRequest implements Constants {
    private Request mInnerRequest;
    private RequestBody mInnerRequestBody;
    private byte[] mEncryptedRequestBytes;

    public EncryptedRequest(Request innerRequest) {
        mInnerRequest = innerRequest;
        mInnerRequestBody = mInnerRequest.body();
    }

    public Request build() {
        Buffer buffer = new Buffer();

        try {
            mInnerRequestBody.writeTo(buffer);
        } catch (IOException e) {
        }

        String plainRequest = null;

        try {
            plainRequest = IOUtils.toString(
                    buffer.inputStream(),
                    UTF_8
            );
        } catch (IOException e) {
        }

        final String encryptedRequest = SecurityHelpers.encrypt(plainRequest);
        mEncryptedRequestBytes = encryptedRequest.getBytes(UTF_8);

        return mInnerRequest
                .newBuilder()
                .method(mInnerRequest.method(), new EncryptedRequestBody())
                .tag(null)
                .build();
    }

    private class EncryptedRequestBody extends RequestBody {
        @Override
        public MediaType contentType() {
            return mInnerRequestBody.contentType();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            sink.write(mEncryptedRequestBytes);
        }
    }
}
