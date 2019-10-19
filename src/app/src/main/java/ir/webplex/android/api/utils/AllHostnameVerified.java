package ir.webplex.android.api.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllHostnameVerified implements HostnameVerifier {
    @Override
    public boolean verify(final String hostname, final SSLSession session) {
        return true;
    }
}
