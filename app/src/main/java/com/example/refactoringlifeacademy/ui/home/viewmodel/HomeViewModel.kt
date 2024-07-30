package com.example.refactoringlifeacademy.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.response.DailyOfferResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductFavoriteResponse
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

    private val _dailyOfferState = MutableLiveData<ProductState<DailyOfferResponse>>()
    val dailyOfferState: LiveData<ProductState<DailyOfferResponse>> = _dailyOfferState

    private val _favoriteState = MutableLiveData<ProductState<ProductFavoriteResponse>>()
    val favoriteState: LiveData<ProductState<ProductFavoriteResponse>> = _favoriteState

    fun getProducts(
        idProductType: Int? = null,
        productName: String? = null,
        onlyFavorite: Boolean = false,
        size: Int = 10
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            _productsState.postValue(ProductState.Loading)
            val allProductsList = mutableListOf<Product>()
            var page = 1
            var hasMoreProducts = true

            while (hasMoreProducts) {
                try {
                    val response =
                        repository.getProducts(idProductType, productName, onlyFavorite, page, size)
                    if (response.isSuccessful) {
                        val products = response.body()?.products ?: emptyList()
                        allProductsList.addAll(products)
                        hasMoreProducts = products.size == size
                        page++
                    } else {
                        hasMoreProducts = false
                        _productsState.postValue(ProductState.Error("Failed: ${response.message()}"))
                    }
                } catch (e: Exception) {
                    hasMoreProducts = false
                    _productsState.postValue(ProductState.Error("Error: ${e.message}"))
                }
            }

            _productsState.postValue(
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

    fun getLastUserProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            _lastUserProductState.postValue(ProductState.Loading)
            try {
                val response = repository.getLastUserProduct()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _lastUserProductState.postValue(ProductState.Success(it))
                    } ?: _lastUserProductState.postValue(ProductState.Error("Empty response body"))
                } else {
                    _lastUserProductState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                _lastUserProductState.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }

    fun getProductTypes() {
        CoroutineScope(Dispatchers.IO).launch {
            _productTypesState.postValue(ProductState.Loading)
            try {
                val response = repository.getProductTypes()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productTypesState.postValue(ProductState.Success(it))
                    } ?: _productTypesState.postValue(ProductState.Error("Empty response body"))
                } else {
                    _productTypesState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                _productTypesState.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }

    fun getDailyOffer() {
        CoroutineScope(Dispatchers.IO).launch {
            _dailyOfferState.postValue(ProductState.Loading)
            try {
                val response = repository.getDailyOffer()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _dailyOfferState.postValue(ProductState.Success(it))
                    } ?: _dailyOfferState.postValue(ProductState.Error("Empty response body"))
                } else {
                    _dailyOfferState.postValue(ProductState.Error("Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                _dailyOfferState.postValue(ProductState.Error("Error: ${e.message}"))
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
