package com.example.testapp.api

import com.example.testapp.response.Data
import com.example.testapp.response.ItemResponse
import com.example.testapp.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("users")
    fun items(@Query("offset")offset:String,
    @Query("limit")limit:String):Call<ItemResponse>
}