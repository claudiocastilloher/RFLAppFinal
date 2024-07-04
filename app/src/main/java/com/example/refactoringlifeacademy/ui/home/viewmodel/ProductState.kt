package com.example.refactoringlifeacademy.ui.home.viewmodel

sealed class ProductState<out T> {
    data class Success<T>(val data: T) : ProductState<T>()
    data class Error(val message: String) : ProductState<Nothing>()
    object Loading : ProductState<Nothing>()
}