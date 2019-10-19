package ir.webplex.android.api.utils;

import ir.webplex.android.core.SessionManager;
import okhttp3.HttpUrl;
import retrofit2.BaseUrl;

public class SessionBasedUrlProvider implements BaseUrl {
    @Override
    public HttpUrl url() {
        return HttpUrl.parse(SessionManager.getInstance().getBaseUri());
    }
}
