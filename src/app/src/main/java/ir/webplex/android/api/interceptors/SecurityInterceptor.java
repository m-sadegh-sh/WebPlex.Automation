package ir.webplex.android.api.interceptors;

import java.io.IOException;

import ir.webplex.android.api.utils.DecryptedResponse;
import ir.webplex.android.api.utils.EncryptedRequest;
import ir.webplex.android.core.Constants;
import okhttp3.Interceptor;
import okhttp3.Response;

public class SecurityInterceptor implements Interceptor, Constants {
    public static final String TAG = SecurityInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        EncryptedRequest securedRequest = new EncryptedRequest(chain.request());

        Response response = chain.proceed(securedRequest.build());

        if (response.isSuccessful())
            return new DecryptedResponse(response)
                    .build();

        return response;
    }
}
