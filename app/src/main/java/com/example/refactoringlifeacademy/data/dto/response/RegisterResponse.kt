package com.example.refactoringlifeacademy.data.dto.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("accessToken")
    val token: String?
)
