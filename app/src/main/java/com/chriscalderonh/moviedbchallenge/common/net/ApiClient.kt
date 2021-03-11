package com.chriscalderonh.moviedbchallenge.common.net

import com.chriscalderonh.moviedbchallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiClient<T> {

    open lateinit var restAPI: Class<T>
    open lateinit var baseURL: String

    private val client =
        try {
            val okHttpBuilder = OkHttpClient.Builder()
            val httpLogInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else httpLogInterceptor.level = HttpLoggingInterceptor.Level.NONE
            okHttpBuilder.addInterceptor(httpLogInterceptor)
            okHttpBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }


    val apiService: T
        get() {
            return Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(restAPI)
        }
}