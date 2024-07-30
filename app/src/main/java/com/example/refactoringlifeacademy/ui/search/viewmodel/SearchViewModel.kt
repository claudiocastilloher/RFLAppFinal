package com.example.refactoringlifeacademy.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: ProductRepository = ProductRepository()) :
    ViewModel() {

    private val _allProducts = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>> get() = _allProducts

    private val _searchState = MutableLiveData<ProductState<ProductsResponse>>()
    val searchState: LiveData<ProductState<ProductsResponse>> = _searchState

    private val _favoriteState = MutableLiveData<ProductState<Void>>()
    val favoriteState: LiveData<ProductState<Void>> = _favoriteState

    fun loadAllProducts(
        idProductType: Int? = null,
        productName: String? = null,
        onlyFavorite: Boolean = false,
        size: Int = 10
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            _searchState.postValue(ProductState.Loading)
            val allProductsList = mutableListOf<Product>()
            var currentPage = 1
            var hasMoreProducts = true

            while (hasMoreProducts) {
                try {
                    val response = searchRepository.getProducts(
                        idProductType,
                        productName,
                        onlyFavorite,
                        currentPage,
                        size
                    )
                    if (response.isSuccessful) {
                        val products = response.body()?.products ?: emptyList()
                        allProductsList.addAll(products)
                        hasMoreProducts = products.size == size
                        currentPage++
                    } else {
                        hasMoreProducts = false
                        _searchState.postValue(ProductState.Error("Failed: ${response.message()}"))
                    }
                } catch (e: Exception) {
                    hasMoreProducts = false
                    _searchState.postValue(ProductState.Error("Error: ${e.message}"))
                }
            }

            _allProducts.postValue(allProductsList)
            _searchState.postValue(
                ProductState.Success(
                    ProductsResponse(
                        page = null,
                        size = allProductsList.size,
                        totalPages = null,
                        totalProducts = allProductsList.size,
                        products = allProductsList
                    )
                )
            )
        }
    }

    fun markProductAsFavorite(idProduct: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _favoriteState.postValue(ProductState.Loading)
            try {
                val response = searchRepository.markProductAsFavorite(idProduct)
                if (response.isSuccessful) {
                    _favoriteState.postValue(ProductState.Success(null))
                } else {
                    _favoriteState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                _favoriteState.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }
}
