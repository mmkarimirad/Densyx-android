package com.bs.dental.networking;

import android.util.Log;

import com.bs.dental.BuildConfig;
import com.bs.dental.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bs156 on 09-Dec-16.
 */

public class RetroClient {

    private RetroClient() {
    }

    public static Api getApi() {
        return getClient().create(Api.class);
    }

    private static Retrofit getClient() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttp())
                .build();
    }

    private static OkHttpClient getOkHttp() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.readTimeout(60, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(60, TimeUnit.SECONDS);
        okHttpBuilder.addInterceptor(getInterceptor());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addNetworkInterceptor(loggingInterceptor);
        }

        return okHttpBuilder.build();
    }

    private static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (!NetworkUtil.getToken().isEmpty()) {
                    builder.addHeader("Token", NetworkUtil.getToken());
                }
                Request request = builder
                        .addHeader("Content-Type", "application/json")
                        .addHeader("DeviceId", NetworkUtil.getDeviceId())
                        .addHeader("NST", NetworkUtil.getNst())
                        .method(original.method(), original.body())
                        .build();
                if (BuildConfig.DEBUG) {
                    Log.d("NetworkHeader", "Token: " + request.header("Token") + " DeviceId: " + request.header("DeviceId"));
                }
                return chain.proceed(request);
            }
        };
    }

}
