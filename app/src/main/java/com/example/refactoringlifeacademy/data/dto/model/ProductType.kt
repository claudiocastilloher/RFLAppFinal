package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class ProductType(
    @SerializedName("idProductType") val idProductType: Int?,
    @SerializedName("descripcion") val descripcion: String?
)
