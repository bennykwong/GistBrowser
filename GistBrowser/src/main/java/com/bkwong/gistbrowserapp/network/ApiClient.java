package com.bkwong.gistbrowserapp.network;

import android.content.Context;
import android.util.Log;

import com.bkwong.gistbrowserapp.models.Gist;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static Retrofit webClient;
    private static ApiClient apiClient;
    private List<Call> calls;
    private GitHubAPI api;

    public static ApiClient getApiClient(Context context) {
        if (apiClient == null)
            apiClient = new ApiClient(context);
        return apiClient;
    }

    /**
     * This constructor will prepare retrofit client object if it not already prepared.
     * This will also set temporary interceptor which will intercept HTTP request and will produce
     * response from JSON data files.
     *
     * @param context Context object
     */
    private ApiClient(Context context) {

        calls = new ArrayList<Call>();

        try {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            final SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();
            Authenticator auth = new TokenAuthenticator();
            OkHttpClient client = null;
                client = new OkHttpClient.Builder()
                        .connectTimeout(60L, TimeUnit.SECONDS)
                        .writeTimeout(60L, TimeUnit.SECONDS)
                        .readTimeout(60L, TimeUnit.SECONDS)
                        .addNetworkInterceptor(loggingInterceptor)
                        .sslSocketFactory(sslSocketFactory)
                        .authenticator(auth)
                        .retryOnConnectionFailure(true)
                        .build();


            webClient = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            api = webClient.create(GitHubAPI.class);

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }
    }

    public void getPublicGists(Callback<ArrayList<Gist>> callback) {
        if (api != null) {
            Call<ArrayList<Gist>> call = api.getPublicGists();
            call.enqueue(callback);
            calls.add(call);
        }
    }

    public void getPublicGistsPages(int page, Callback<ArrayList<Gist>> callback) {
        if (api != null) {
            Call<ArrayList<Gist>> call = api.getPublicGistsPage(page);
            call.enqueue(callback);
            calls.add(call);
        }
    }

}
