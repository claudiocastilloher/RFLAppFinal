package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("link")
    val link: String?
)
