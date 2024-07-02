package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.ProductTypesResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.dto.response.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("api/v1/products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("api/v1/products/lastUserProduct")
    suspend fun getLastUserProduct(): Response<SingleProductResponse>

    @GET("api/v1/products-type")
    suspend fun getProductTypes(): Response<ProductTypesResponse>

    @GET("api/v1/products/daily-offer")
    suspend fun getDailyOffer(): Response<SingleProductResponse>
}
