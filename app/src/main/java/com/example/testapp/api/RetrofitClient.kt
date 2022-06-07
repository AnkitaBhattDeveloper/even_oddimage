package com.example.testapp.api

import com.example.testapp.utils.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object {
        var client = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build()


        fun RetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(App.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}