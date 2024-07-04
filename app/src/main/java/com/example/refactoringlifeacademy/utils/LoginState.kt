package com.example.refactoringlifeacademy.utils

import com.example.refactoringlifeacademy.data.dto.response.LoginResponse

sealed class LoginState {
    data class Succes(private val info: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
    data class FormValid(val state: Boolean) : LoginState()
    data object Loading : LoginState()

}