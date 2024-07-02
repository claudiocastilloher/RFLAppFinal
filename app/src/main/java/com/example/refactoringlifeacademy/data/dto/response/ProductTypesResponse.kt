package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.ProductType
import com.google.gson.annotations.SerializedName

data class ProductTypesResponse(
    @SerializedName("productTypes")
    val productTypes: List<ProductType>?
)
