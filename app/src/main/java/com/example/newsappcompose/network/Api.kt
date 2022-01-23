package com.example.newsappcompose.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    private const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "7da5b855d3f947babe924697d940c1c9"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val logging = HttpLoggingInterceptor()

    val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("X-Api-key", API_KEY)
            return@Interceptor chain.proceed(builder.build())
        })
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()

    val retrofitService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }
}