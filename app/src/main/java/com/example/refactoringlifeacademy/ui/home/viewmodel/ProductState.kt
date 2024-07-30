package com.example.refactoringlifeacademy.ui.home.viewmodel

sealed class ProductState<out T> {
    data class Success<out T>(val data: T?) : ProductState<T>()
    data class Error(val message: String) : ProductState<Nothing>()
    data object Loading : ProductState<Nothing>()
}
