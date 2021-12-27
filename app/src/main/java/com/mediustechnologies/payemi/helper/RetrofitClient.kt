package com.mediustechnologies.payemi.helper

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.mediustechnologies.payemi.commons.API
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.jvm.Synchronized
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor(BASE_URL: String) {
    private val retrofit: Retrofit
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(loggin)
        .build()

    //Api_call.APIService postService = null;
    val api: API
        get() = retrofit.create(API::class.java)

    companion object {
        var mInstance: RetrofitClient? = null
        val loggin: HttpLoggingInterceptor
            get() {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                return logging
            }

        @Synchronized
        fun getInstance(BASE_URL: String): RetrofitClient? {
            mInstance = RetrofitClient(BASE_URL)
            return mInstance
        }
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}