package com.example.refactoringlifeacademy.utils

import com.example.refactoringlifeacademy.data.dto.response.LoginResponse
import com.example.refactoringlifeacademy.data.dto.response.RegisterResponse

sealed class StateRegister {

    data class Success(val info:RegisterResponse): StateRegister()
    data class Error(val message: String): StateRegister()
    data object Loading : StateRegister()

    data class FormValid(val state:Boolean): StateRegister()


}