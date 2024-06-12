package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.request.LoginRequest
import com.example.refactoringlifeacademy.data.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class LoginServiceImp {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api-users-c9xg.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<LoginService>()

    fun loginUser(request: LoginRequest): Response<LoginResponse>{
        return service.loginUser(request)
    }
}
