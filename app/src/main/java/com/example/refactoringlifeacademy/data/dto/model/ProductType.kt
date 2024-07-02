package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class ProductType(
    @SerializedName("idType") val idType: Int?,
    @SerializedName("descripcion") val description: String?
)
