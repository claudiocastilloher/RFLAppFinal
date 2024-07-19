package com.example.refactoringlifeacademy.data.repository

import com.example.refactoringlifeacademy.data.dto.response.CommentsResponse
import com.example.refactoringlifeacademy.data.service.CommentServiceImp
import retrofit2.Response

class CommentRepository(private val service: CommentServiceImp = CommentServiceImp()) {

    suspend fun getComments(idProduct: Int): Response<CommentsResponse> {
        return service.getComments(idProduct)
    }
}
