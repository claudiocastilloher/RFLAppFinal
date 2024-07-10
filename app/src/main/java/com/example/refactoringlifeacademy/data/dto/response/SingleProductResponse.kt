package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.Product
import com.google.gson.annotations.SerializedName

data class SingleProductResponse(
    //@SerializedName("product")
    val product: Product?
)
