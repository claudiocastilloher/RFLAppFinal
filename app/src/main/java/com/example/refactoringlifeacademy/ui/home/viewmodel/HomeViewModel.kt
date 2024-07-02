package com.example.refactoringlifeacademy.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.ProductTypesResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.dto.response.SingleProductResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {

    private val _productsState = MutableLiveData<ProductState<ProductsResponse>>()
    val productsState: LiveData<ProductState<ProductsResponse>> = _productsState

    private val _lastUserProductState = MutableLiveData<ProductState<SingleProductResponse>>()
    val lastUserProductState: LiveData<ProductState<SingleProductResponse>> = _lastUserProductState

    private val _productTypesState = MutableLiveData<ProductState<ProductTypesResponse>>()
    val productTypesState: LiveData<ProductState<ProductTypesResponse>> = _productTypesState

    private val _dailyOfferState = MutableLiveData<ProductState<SingleProductResponse>>()
    val dailyOfferState: LiveData<ProductState<SingleProductResponse>> = _dailyOfferState

    fun getProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            _productsState.postValue(ProductState.Loading)
            val response = repository.getProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    _productsState.postValue(ProductState.Success(it))
                } ?: _productsState.postValue(ProductState.Error("Empty response body"))
            } else {
                _productsState.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }

    fun getLastUserProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            _lastUserProductState.postValue(ProductState.Loading)
            val response = repository.getLastUserProduct()
            if (response.isSuccessful) {
                response.body()?.let {
                    _lastUserProductState.postValue(ProductState.Success(it))
                } ?: _lastUserProductState.postValue(ProductState.Error("Empty response body"))
            } else {
                _lastUserProductState.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }

    fun getProductTypes() {
        CoroutineScope(Dispatchers.IO).launch {
            _productTypesState.postValue(ProductState.Loading)
            val response = repository.getProductTypes()
            if (response.isSuccessful) {
                response.body()?.let {
                    _productTypesState.postValue(ProductState.Success(it))
                } ?: _productTypesState.postValue(ProductState.Error("Empty response body"))
            } else {
                _productTypesState.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }

    fun getDailyOffer() {
        CoroutineScope(Dispatchers.IO).launch {
            _dailyOfferState.postValue(ProductState.Loading)
            val response = repository.getDailyOffer()
            if (response.isSuccessful) {
                response.body()?.let {
                    _dailyOfferState.postValue(ProductState.Success(it))
                } ?:  _dailyOfferState.postValue(ProductState.Error("Empty response body"))
            } else {
                _dailyOfferState.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }
}
