package com.example.refactoringlifeacademy.data.dto.response

import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("accessToken")
    val token: String?
)
