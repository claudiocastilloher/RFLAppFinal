package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    private val token: String = UserProduct.userToken ?: ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }

}
