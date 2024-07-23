package com.example.refactoringlifeacademy.data.repository

import com.example.refactoringlifeacademy.data.dto.response.FinanceMethodsResponse
import com.example.refactoringlifeacademy.data.service.FinanceServiceImp
import retrofit2.Response

class FinanceRepository(private val service: FinanceServiceImp = FinanceServiceImp()) {
    suspend fun getFinanceMethods(): Response<FinanceMethodsResponse> {
        return service.getFinanceMethods()
    }
}