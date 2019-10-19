package ir.webplex.android.api;

import java.util.concurrent.TimeUnit;

import ir.webplex.android.api.interceptors.DelayInterceptor;
import ir.webplex.android.api.interceptors.LoggingInterceptor;
import ir.webplex.android.api.interceptors.SecurityInterceptor;
import ir.webplex.android.api.services.AuthenticateService;
import ir.webplex.android.api.services.SearchService;
import ir.webplex.android.api.utils.AllHostnameVerified;
import ir.webplex.android.api.utils.SSLFactoryProvider;
import ir.webplex.android.api.utils.SessionBasedUrlProvider;
import ir.webplex.android.core.Constants;
import ir.webplex.android.core.logging.Logger;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public class ServiceBuilder implements Constants {
    public static final String TAG = ServiceBuilder.class.getSimpleName();

    private static ServiceBuilder mInstance;
    private Retrofit mRetrofit;
    private AuthenticateService mAuthenticateService;
    private SearchService mSearchService;

    private ServiceBuilder() {
        Logger.onInitiating(TAG);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new SecurityInterceptor())
                .addInterceptor(new DelayInterceptor())
                .connectTimeout(TIMEOUTS_CONNECT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUTS_WRITE, TimeUnit.SECONDS)
                .readTimeout(TIMEOUTS_READ, TimeUnit.SECONDS)
                .sslSocketFactory(SSLFactoryProvider.getFactory())
                .hostnameVerifier(new AllHostnameVerified())
                .certificatePinner(CertificatePinner.DEFAULT)
                .build();

        mRetrofit = new Builder()
                .baseUrl(new SessionBasedUrlProvider())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mAuthenticateService = mRetrofit.create(AuthenticateService.class);
        mSearchService = mRetrofit.create(SearchService.class);

        Logger.onInited(TAG);
    }

    public static synchronized ServiceBuilder getInstance() {
        if (mInstance == null) {
            Logger.onInstanceCreating(TAG);
            mInstance = new ServiceBuilder();
        }

        return mInstance;
    }

    public AuthenticateService getAuthenticateService() {
        return mAuthenticateService;
    }

    public SearchService getSearchService() {
        return mSearchService;
    }
}