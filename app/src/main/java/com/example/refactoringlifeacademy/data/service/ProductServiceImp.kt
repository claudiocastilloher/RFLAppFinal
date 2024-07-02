package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.ProductTypesResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.dto.response.SingleProductResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProductServiceImp {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://stoplight.io/mocks/reinierdearmas/api-app-final-rfa/428619854/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<ProductService>()

    suspend fun getProducts(
        idProductType: Int? = null,
        productName: String? = null,
        onlyFavorite: Boolean = false,
        page: Int = 1,
        size: Int = 10
    ): Response<ProductsResponse> {
        return service.getProducts(idProductType, productName, onlyFavorite, page, size)
    }

    suspend fun getLastUserProduct(): Response<SingleProductResponse> {
        return service.getLastUserProduct()
    }

    suspend fun getProductTypes(): Response<ProductTypesResponse> {
        return service.getProductTypes()
    }

    suspend fun getDailyOffer(): Response<SingleProductResponse> {
        return service.getDailyOffer()
    }

}
