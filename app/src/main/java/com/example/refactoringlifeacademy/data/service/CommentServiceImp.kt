package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.CommentsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class CommentServiceImp {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://stoplight.io/mocks/reinierdearmas/api-app-final-rfa/451467774/")//CAMBIAR LA BASE URL CUANDO BACK LA TENGA DISPONIBLE
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<CommentService>()

    suspend fun getComments(idProduct: Int): Response<CommentsResponse>{
        return service.getComments(idProduct)
    }
}
