package ir.webplex.android.api.utils;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SSLFactoryProvider {
    public static SSLSocketFactory getFactory() {
        final TrustManager[] tm = new TrustManager[]{new NullTrustManager()};

        SSLContext context = null;
        try {
            context = SSLContext.getInstance("SSL");
            context.init(null, tm, new SecureRandom());
        } catch (GeneralSecurityException ex) {
        }

        return context != null ? context.getSocketFactory() : null;
    }
}

