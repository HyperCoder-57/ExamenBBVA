package com.stark.dogboard.network

import com.stark.dogboard.model.DogResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceDog {
    @GET("breeds/image/random")
    fun getRandomDogImage(): Call<DogResponse>
}