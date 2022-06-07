package com.example.testapp.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.api.RetrofitService
import com.example.testapp.response.Data
import com.example.testapp.response.ItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemRepository(
    private val retrofitService: RetrofitService, private val applicationContext: Context,
) {
    private val _dataResponse = MutableLiveData<ItemResponse>()
    val dataResponse: LiveData<ItemResponse>
        get() = _dataResponse

    fun items(
        offset: String
    ) {
            val result = retrofitService.items(offset = offset,limit = "10")
            result.enqueue(object : Callback<ItemResponse> {
                override fun onResponse(
                    call: Call<ItemResponse>,
                    response: Response<ItemResponse>,
                ) {
                    if (response.body() != null) {
                        _dataResponse.postValue(response.body())
                    } else {
                        _dataResponse.postValue(null)

                    }
                }

                override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "${t.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                }
            })


    }
}