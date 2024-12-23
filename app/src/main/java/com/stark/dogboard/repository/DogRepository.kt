package com.stark.dogboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stark.dogboard.model.DogResponse
import com.stark.dogboard.network.ApiServiceDog
import com.stark.dogboard.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository {
    private val apiServiceLogin = RetrofitClient.instanceDog.create(ApiServiceDog::class.java)

    fun obtRandomDogImg(): LiveData<DogResponse?> {
        val dogImageResponse = MutableLiveData<DogResponse?>()

        apiServiceLogin.getRandomDogImage().enqueue(object : Callback<DogResponse> {
            override fun onResponse(call: Call<DogResponse>, resp: Response<DogResponse>) {
                if (resp.isSuccessful) {
                    dogImageResponse.value = resp.body()
                } else {
                    dogImageResponse.value = null
                }
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                dogImageResponse.value = null
            }

        })
        return dogImageResponse
    }
}