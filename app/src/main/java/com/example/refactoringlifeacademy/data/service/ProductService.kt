package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.DailyOfferResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductByIdResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductTypesResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.dto.response.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("api/v1/products")
    suspend fun getProducts(
        @Query("idProductType") idProductType: Int? = null,
        @Query("productName") productName: String? = null,
        @Query("onlyFavorite") onlyFavorite: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Response<ProductsResponse>

    @GET("api/v1/products/lastUserProduct")
    suspend fun getLastUserProduct(): Response<SingleProductResponse>

    @GET("api/v1/product-types")
    suspend fun getProductTypes(): Response<ProductTypesResponse>

    @GET("api/v1/products/daily-offer")
    suspend fun getDailyOffer(): Response<DailyOfferResponse>

    @PUT("api/v1/products/{idProduct}/favorite")
    suspend fun markProductAsFavorite(@Path("idProduct") idProduct: Int): Response<Void>

    @GET("api/v1/products/{idProduct}")
    suspend fun getProductById(@Path("idProduct") idProduct: Int): Response<ProductByIdResponse>
}
