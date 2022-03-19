package com.example.weatherapp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClientImplementation {

    private static Retrofit retro;
    private static OkHttpClient client;

    private static final int REQUEST_TIMEOUT = 60;


    public static Retrofit retrofitBuilder() {
        if (client != null) {
            if (retro != null) {
                return retro;
            } else {
                retro = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .client(client).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        } else {
            // TODO: initialization
            OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
            // https://stackoverflow.com/questions/42835315/httplogginginterceptor-for-http-request-response-logging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json");
                    // Request request = requestBuilder.build();
                    return chain.proceed(requestBuilder.build());
                }
            });
            client = httpClient.build();
        }
        return retro;
    }
}
