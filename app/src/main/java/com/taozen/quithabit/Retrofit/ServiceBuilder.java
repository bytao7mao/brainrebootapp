package com.taozen.quithabit.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

    private static final String URL = "https://pyflasktao.herokuapp.com/books/";

    //logger
    private static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    //logger[END]
    //create okHttp Client
    private static OkHttpClient.Builder okHttp =
            new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    //okHttp Client[END]

    //Retrofit builder
    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());
    //Retrofit builder[END]
    private static Retrofit retrofit = builder.build();
    public static <S> S builderService(Class<S> serviceType){
        return retrofit.create(serviceType);
    }
}
