package com.stark.dogboard.network
import com.stark.dogboard.model.LoginRequest
import com.stark.dogboard.model.LoginUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceLogin {
    @POST("login")
    fun login(@Body request: LoginRequest): Call <LoginUserResponse>
}