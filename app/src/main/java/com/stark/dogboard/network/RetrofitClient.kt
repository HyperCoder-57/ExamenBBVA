package com.stark.dogboard.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val BASE_URL_LOGIN = "https://private-6eaf4c-examenbbva.apiary-mock.com/"
    private const val BASE_URL_DOG = "https://dog.ceo/api/"

    val instanceLogin: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instanceDog: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL_DOG)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}