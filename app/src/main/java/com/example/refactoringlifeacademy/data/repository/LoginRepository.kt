package com.example.refactoringlifeacademy.data.repository

import com.example.refactoringlifeacademy.data.dto.request.LoginRequest
import com.example.refactoringlifeacademy.data.dto.response.LoginResponse
import com.example.refactoringlifeacademy.data.service.LoginServiceImp
import retrofit2.Response

class LoginRepository(private val service: LoginServiceImp = LoginServiceImp()) {

    fun loginUser(request: LoginRequest) :Response<LoginResponse>{
        return service.loginUser(request)
    }

}
