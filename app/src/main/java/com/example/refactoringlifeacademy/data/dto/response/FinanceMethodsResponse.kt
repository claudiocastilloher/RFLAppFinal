package com.example.refactoringlifeacademy.data.dto.response

import com.example.refactoringlifeacademy.data.dto.model.FinanceMethods
import com.google.gson.annotations.SerializedName

data class FinanceMethodsResponse (
        @SerializedName("paymentMethods")
        val financeMethods: List<FinanceMethods>?
    )
