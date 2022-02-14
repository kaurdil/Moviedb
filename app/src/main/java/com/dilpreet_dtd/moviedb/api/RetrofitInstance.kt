package com.dilpreet_dtd.moviedb.api

import com.dilpreet_dtd.moviedb.util.Constants.Companion.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private val retrofit by lazy{
            val logging=HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            //create OkHttp Client
            val client=OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
            //create Retrofit builder
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy{
            retrofit.create(MoviesApi::class.java)
        }
    }
}