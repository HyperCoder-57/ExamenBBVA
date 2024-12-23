package com.stark.dogboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stark.dogboard.model.LoginRequest
import com.stark.dogboard.model.LoginUserResponse
import com.stark.dogboard.network.ApiServiceLogin
import com.stark.dogboard.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    private val apiServiceLogin = RetrofitClient.instanceLogin.create(ApiServiceLogin::class.java)

    fun login(request: LoginRequest): LiveData<LoginUserResponse?> {
        val loginUserResponse = MutableLiveData<LoginUserResponse?>()

        apiServiceLogin.login(request).enqueue(object : Callback<LoginUserResponse> {
            override fun onResponse(call: Call<LoginUserResponse>, response: Response<LoginUserResponse>) {
                if (response.isSuccessful) {
                    loginUserResponse.value = response.body()
                } else {
                    loginUserResponse.value = null
                }
            }

            override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                loginUserResponse.value = null
            }
        })

        return loginUserResponse
    }

}