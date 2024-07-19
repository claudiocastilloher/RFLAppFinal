package com.example.refactoringlifeacademy.ui.commentFragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.response.CommentsResponse
import com.example.refactoringlifeacademy.data.repository.CommentRepository
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: CommentRepository = CommentRepository()): ViewModel() {
    private val _comments = MutableLiveData<ProductState<CommentsResponse>>()
    val comments: LiveData<ProductState<CommentsResponse>> = _comments

    fun getComments(idProduct: Int){
        CoroutineScope(Dispatchers.IO).launch {
            _comments.postValue(ProductState.Loading)
            val response = repository.getComments(idProduct)
            if (response.isSuccessful) {
                response.body()?.let {
                    _comments.postValue(ProductState.Success(it))
                }?: _comments.postValue(ProductState.Error("Empty response body"))
            }else{
                _comments.postValue(ProductState.Error("Failed: ${response.message()}"))
            }
        }
    }
}
