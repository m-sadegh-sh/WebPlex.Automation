package ir.webplex.android.api.interceptors;

import java.io.IOException;

import ir.webplex.android.core.Constants;
import okhttp3.Interceptor;
import okhttp3.Response;

public class DelayInterceptor implements Interceptor, Constants {
    public static final String TAG = DelayInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Thread.sleep(DELAYS_HTTP_REQUEST);
        } catch (InterruptedException e) {
        } finally {
            return chain.proceed(chain.request());
        }
    }
}
