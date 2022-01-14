package com.mediustechnologies.payemi.helper;

import android.content.Context;

import com.mediustechnologies.payemi.commons.API;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static RetrofitClient mInstance;
    private Retrofit retrofit;
    private Context activity_context;

    public RetrofitClient(Context activity_context){
        this.activity_context = activity_context;
    }


    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            .addInterceptor(getLoggin())
            .addInterceptor(new ErrorInterceptor(activity_context))
            .build();


    public static HttpLoggingInterceptor getLoggin() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
    private RetrofitClient(String BASE_URL) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public synchronized RetrofitClient getInstance(String BASE_URL) {
        mInstance = new RetrofitClient(BASE_URL);
        return mInstance;
    }

    //Api_call.APIService postService = null;

    public API getApi() {
        return retrofit.create(API.class);
    }


}
