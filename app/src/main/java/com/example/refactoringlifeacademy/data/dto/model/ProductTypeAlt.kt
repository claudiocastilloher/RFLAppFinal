package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class ProductTypeAlt(
    @SerializedName("idProductType") val idProductType: Int?,
    @SerializedName("description") val description: String?
)
