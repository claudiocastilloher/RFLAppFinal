package com.example.refactoringlifeacademy.ui.similar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.ProductFavoriteResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimilarViewModel (private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _productsSimilarState = MutableLiveData<ProductState<ProductsResponse>>()
    val productsSimilarState: LiveData<ProductState<ProductsResponse>> = _productsSimilarState
    private val _favoriteState = MutableLiveData<ProductState<ProductFavoriteResponse>>()
    val favoriteState: LiveData<ProductState<ProductFavoriteResponse>> = _favoriteState

    fun getSimilarProducts(idProduct: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            _productsSimilarState.postValue(ProductState.Loading)
            try {
                val response = repository.getSimilarProducts(idProduct)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productsSimilarState.postValue(ProductState.Success(it))
                    } ?: _productsSimilarState.postValue(ProductState.Error("Empty response body"))
                } else {
                    _productsSimilarState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            }catch (e: Exception){
                _productsSimilarState.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }

    fun markProductAsFavorite(idProduct: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _favoriteState.postValue(ProductState.Loading)

            try {
                val response = repository.markProductAsFavorite(idProduct)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _favoriteState.postValue(ProductState.Success(it))
                    } ?: _favoriteState.postValue(ProductState.Error("Empty response body"))
                } else {
                    _favoriteState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                _favoriteState.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }
}