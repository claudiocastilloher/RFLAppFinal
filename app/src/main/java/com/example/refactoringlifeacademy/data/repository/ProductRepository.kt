package com.example.refactoringlifeacademy.data.repository

import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.response.DailyOfferResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductByIdResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductTypesResponse
import com.example.refactoringlifeacademy.data.dto.response.ProductsResponse
import com.example.refactoringlifeacademy.data.dto.response.SingleProductResponse
import com.example.refactoringlifeacademy.data.service.ProductServiceImp
import retrofit2.Response

class ProductRepository(private val service: ProductServiceImp = ProductServiceImp()) {

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
}
