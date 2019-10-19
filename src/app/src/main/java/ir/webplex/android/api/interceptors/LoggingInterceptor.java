package ir.webplex.android.api.interceptors;

import java.io.IOException;

import ir.webplex.android.core.Constants;
import ir.webplex.android.core.logging.Logger;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class LoggingInterceptor implements Interceptor, Constants {
    public static final String TAG = LoggingInterceptor.class.getSimpleName();

    private final HttpLoggingInterceptor mInnerInterceptor;

    public LoggingInterceptor() {
        InnerLogger logger = new InnerLogger();
        mInnerInterceptor = new HttpLoggingInterceptor(logger);
        mInnerInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return mInnerInterceptor.intercept(chain);
    }

    private class InnerLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Logger.onHttp(TAG, message);
        }
    }
}
