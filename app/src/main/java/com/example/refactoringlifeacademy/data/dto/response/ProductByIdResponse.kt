package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.data.dto.model.ProductType
import com.google.gson.annotations.SerializedName

data class ProductByIdResponse(
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
    @SerializedName("images")
    val images: List<Image>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("largeDescription")
    val largeDescription: String?,
    @SerializedName("isFavorite")
    val isFavorite: Boolean?
)
