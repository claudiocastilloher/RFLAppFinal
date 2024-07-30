package com.example.refactoringlifeacademy.ui.imageFragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.ProductByIdResponse
import com.example.refactoringlifeacademy.data.repository.ProductRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewmodel(private val repository: ProductRepository = ProductRepository()) : ViewModel() {

    private val _data = MutableLiveData<ProductState<ProductByIdResponse>>()
    val data: LiveData<ProductState<ProductByIdResponse>> = _data

    fun getProductById(idProduct: Int){
        CoroutineScope(Dispatchers.IO).launch{
            _data.postValue(ProductState.Loading)
            try {
                val response = repository.getProductById(idProduct)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _data.postValue(ProductState.Success(it))
                    } ?: _data.postValue(ProductState.Error("No Data"))
                } else {
                    _data.postValue(ProductState.Error("Error Service"))
                }
            }catch (e: Exception){
                _data.postValue(ProductState.Error("Error: ${e.message}"))
            }
        }
    }
}
