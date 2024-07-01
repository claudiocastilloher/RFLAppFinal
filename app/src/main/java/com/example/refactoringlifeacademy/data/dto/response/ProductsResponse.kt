package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.Product
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?,
    @SerializedName("totalProducts")
    val totalProducts: Int?,
    @SerializedName("products")
    val products: List<Product>?
)
