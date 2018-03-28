package com.xxx.winio.network;

import com.xxx.winio.utils.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }

    public static String get(String url) throws IOException {
        Logger.i("NET URL:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            Logger.i("NET BODY:" + body);
            return body;
        } catch (IOException e) {
            throw e;
        }
    }
}
