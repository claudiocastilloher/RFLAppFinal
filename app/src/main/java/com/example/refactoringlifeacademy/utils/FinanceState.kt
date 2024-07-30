package com.example.refactoringlifeacademy.utils

import com.example.refactoringlifeacademy.data.dto.response.FinanceMethodsResponse

sealed class FinanceState {
    data class Success(val info: FinanceMethodsResponse): FinanceState()
    data class Error(val message: String):FinanceState()
    data object Loading : FinanceState()
}
