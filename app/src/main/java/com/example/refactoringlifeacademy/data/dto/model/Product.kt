package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("idProduct")
    val idProduct: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("productType")
    val productType: ProductType?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("isFavorite")
    val isFavorite: Boolean?
)
