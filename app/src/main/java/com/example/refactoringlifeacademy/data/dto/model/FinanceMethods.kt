package com.example.refactoringlifeacademy.data.dto.model

import com.google.gson.annotations.SerializedName

data class FinanceMethods (
    @SerializedName("idPaymentMethod") val idPaymentMethod: Int?,
    @SerializedName("entity") val entity: String?,
    @SerializedName("installments") val installments: List<FinanceInstallments>?
)