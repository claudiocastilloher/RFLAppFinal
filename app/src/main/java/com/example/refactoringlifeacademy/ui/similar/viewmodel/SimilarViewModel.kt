package com.example.refactoringlifeacademy.ui.similar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimilarViewModel (private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _productsSimilarState = MutableLiveData<ProductState<ProductsResponse>>()
    val productsSimilarState: LiveData<ProductState<ProductsResponse>> = _productsSimilarState

    fun getSimilarProducts(idProduct: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            _productsSimilarState.postValue(ProductState.Loading)
            val response = repository.getSimilarProducts(idProduct)
            if (response.isSuccessful) {
                response.body()?.let {
                    _productsSimilarState.postValue(ProductState.Success(it))
                } ?: _productsSimilarState.postValue(ProductState.Error("Empty response body"))
            } else {
                _productsSimilarState.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }
}