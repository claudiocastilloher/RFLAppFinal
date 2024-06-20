package com.example.refactoringlifeacademy.utils

import com.example.refactoringlifeacademy.data.dto.response.LoginResponse

sealed class StateRegister {

    data class Success(private val info:String): StateRegister()
    data class Error(val message: String): StateRegister()
    data object Loading : StateRegister()


}