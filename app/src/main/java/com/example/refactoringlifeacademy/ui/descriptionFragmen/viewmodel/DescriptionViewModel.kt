package com.example.refactoringlifeacademy.ui.descriptionFragmen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.ProductByIdResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {
    private val _productByIdState = MutableLiveData<ProductState<ProductByIdResponse>>()
    val productByIdState: LiveData<ProductState<ProductByIdResponse>> = _productByIdState

    fun getProductByID(idProduc: Int){
        CoroutineScope(Dispatchers.IO).launch {
            _productByIdState.postValue(ProductState.Loading)
            try {
                val response = repository.getProductById(idProduc)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productByIdState.postValue(ProductState.Success(it))
                    } ?: _productByIdState.postValue(ProductState.Error("Empty response body"))
                } else {
                    _productByIdState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            }catch (e: Exception){
                _productByIdState.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }
}