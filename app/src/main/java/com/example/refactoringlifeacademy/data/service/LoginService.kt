package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.request.LoginRequest
import com.example.refactoringlifeacademy.data.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
}
