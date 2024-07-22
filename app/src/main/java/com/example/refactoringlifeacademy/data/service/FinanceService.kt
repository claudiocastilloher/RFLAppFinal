package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.FinanceMethodsResponse
import retrofit2.Response
import retrofit2.http.GET

interface FinanceService {
    @GET("/api/v1/payment-methods")
    suspend fun getFinanceMethods(): Response<FinanceMethodsResponse>
}