package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class FinanceInstallments(
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("interest") val interest: String?
)
