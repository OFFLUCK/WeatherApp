package com.example.weatherapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiClientImplementation {

    private static Retrofit retro;
    private static OkHttpClient client;


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

            var interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) {
                    Request original = new chain.
                }
            })
        }

    }

}
