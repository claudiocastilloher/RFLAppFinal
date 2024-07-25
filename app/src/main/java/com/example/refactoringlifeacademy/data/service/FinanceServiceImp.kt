package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.FinanceMethodsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class FinanceServiceImp {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api-payments-1ztc.onrender.com//")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<FinanceService>()

    suspend fun getFinanceMethods(): Response<FinanceMethodsResponse> {
        return service.getFinanceMethods()
    }
}