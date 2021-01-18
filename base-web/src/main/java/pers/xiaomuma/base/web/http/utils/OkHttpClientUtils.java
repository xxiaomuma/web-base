package pers.xiaomuma.base.web.http.utils;


import okhttp3.ConnectionPool;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import okhttp3.OkHttpClient.Builder;
import java.util.concurrent.TimeUnit;

public class OkHttpClientUtils {

    private static SSLSocketFactory sslSocketFactory = null;
    private static X509TrustManager trustManager = null;
    private static SSLSocketFactory sslSocketFactoryVerifyCa = null;
    private static X509TrustManager trustManagerVerifyCa = null;

    public OkHttpClientUtils() {
    }

    public static Builder enhanceBuilderHttps(boolean needVerifyCa, InputStream caInputStream, String cAalias, Builder builder) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        httpsHelper(needVerifyCa, caInputStream, cAalias);
        if (needVerifyCa) {
            builder.sslSocketFactory(sslSocketFactoryVerifyCa, trustManagerVerifyCa);
            return builder;
        } else {
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.hostnameVerifier((hostname, session) -> {
                return true;
            });
            return builder;
        }
    }

    private static ConnectionPool pool() {
        return new ConnectionPool(100, 5L, TimeUnit.MINUTES);
    }

    private static void httpsHelper(boolean needVerifyCa, InputStream caInputStream, String cAalias) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        if (needVerifyCa) {
            KeyStore keyStore = getKeyStore(caInputStream, cAalias);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length == 1 && trustManagers[0] instanceof X509TrustManager) {
                trustManagerVerifyCa = (X509TrustManager)trustManagers[0];
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init((KeyManager[])null, new TrustManager[]{trustManagerVerifyCa}, new SecureRandom());
                sslSocketFactoryVerifyCa = sslContext.getSocketFactory();
            } else {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
        } else {
            trustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init((KeyManager[])null, new TrustManager[]{trustManager}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        }
    }

    private static KeyStore getKeyStore(InputStream caInputStream, String cAalias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry(cAalias, certificateFactory.generateCertificate(caInputStream));
        return keyStore;
    }
}
