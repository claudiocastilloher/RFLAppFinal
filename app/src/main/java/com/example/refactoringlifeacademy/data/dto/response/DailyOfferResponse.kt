package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.data.dto.model.ProductType
import com.google.gson.annotations.SerializedName

data class DailyOfferResponse(
    @SerializedName("idProduct")
    val idProduct: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("productType")
    val productType: ProductType?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("images")
    val images: List<Image>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dailyOffer")
    val dailyOffer: Boolean?,
    @SerializedName("isFavorite")
    val isFavorite: Boolean?
)
