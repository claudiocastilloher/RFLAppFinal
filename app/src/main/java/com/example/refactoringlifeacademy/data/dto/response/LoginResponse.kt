package com.example.refactoringlifeacademy.data.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val token: String?
)
