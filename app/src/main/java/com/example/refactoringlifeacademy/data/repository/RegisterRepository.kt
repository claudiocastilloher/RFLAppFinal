package com.example.refactoringlifeacademy.data.repository

import com.example.refactoringlifeacademy.data.dto.request.RegisterRequest
import com.example.refactoringlifeacademy.data.dto.response.RegisterResponse
import com.example.refactoringlifeacademy.data.service.RegisterServiceImplement
import retrofit2.Response

class RegisterRepository(private val service: RegisterServiceImplement = RegisterServiceImplement()) {

    fun registerUser(request: RegisterRequest) : Response<RegisterResponse>{
        return service.registerUser(request)
    }
}
