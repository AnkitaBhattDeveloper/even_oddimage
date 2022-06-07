package com.example.testapp.utils

import android.app.Application
import com.example.testapp.api.RetrofitClient
import com.example.testapp.api.RetrofitService
import com.example.testapp.repository.ItemRepository

class App:Application() {
    lateinit var itemRepository: ItemRepository


    companion object {

        const val BASE_URL = "http://sd2-hiring.herokuapp.com/api/"
    }

    override fun onCreate() {
        super.onCreate()
        initialize()

    }


    fun initialize() {
        val service = RetrofitClient.RetrofitInstance()
            .create(RetrofitService::class.java)
        itemRepository = ItemRepository(service, applicationContext)



    }
}