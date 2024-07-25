package com.example.refactoringlifeacademy.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchVielModel(private val searchRepository: ProductRepository = ProductRepository()): ViewModel() {
    private val _searchState = MutableLiveData<ProductState<ProductsResponse>>()
    val searchState: LiveData<ProductState<ProductsResponse>> = _searchState

    fun searchProducts(
        idProductType: Int? = null,
        productName: String? = null,
        onlyFavorite: Boolean = false,
        page: Int = 1,
        size: Int = 10
    ) {
        if (page < 1 || size !in 1..50) {
            _searchState.postValue(ProductState.Error("Invalid pagination parameters"))
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            _searchState.postValue(ProductState.Loading)
            val response = searchRepository.getProducts(idProductType, productName, onlyFavorite, page, size)
            if (response.isSuccessful) {
                response.body()?.let {
                    _searchState.postValue(ProductState.Success(it))
                } ?: _searchState.postValue(ProductState.Error("Empty response body"))
            } else {
                _searchState.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }
}
