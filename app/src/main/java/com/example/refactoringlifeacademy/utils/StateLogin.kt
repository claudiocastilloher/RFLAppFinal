package com.example.refactoringlifeacademy.utils

import com.example.refactoringlifeacademy.data.dto.response.LoginResponse

sealed class StateLogin {

    data class Succes(private val info: LoginResponse): StateLogin()
    data class Error(val message: String): StateLogin()
    data object Loading : StateLogin()


}