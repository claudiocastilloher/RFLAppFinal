package com.example.refactoringlifeacademy.data.dto.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val email: String,
    val password: String
)
