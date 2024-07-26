package com.example.refactoringlifeacademy.data.service

import com.example.refactoringlifeacademy.data.dto.response.DailyOfferResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductByIdResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductTypesResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.dto.response.SingleProductResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProductServiceImp {

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api-products-fe4p.onrender.com/")
        .client(client)
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

    suspend fun getDailyOffer(): Response<DailyOfferResponse> {
        return service.getDailyOffer()
    }

    suspend fun markProductAsFavorite(idProduct: Int): Response<Void> {
        return service.markProductAsFavorite(idProduct)
    }

    suspend fun getProductById(idProduct: Int): Response<ProductByIdResponse> {
        return service.getProductById(idProduct)
    }

    suspend fun getSimilarProducts(idProduct: Int): Response<ProductsResponse> {
        return service.getSimilarProducts(idProduct)
    }

}
