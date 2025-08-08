package com.example.shopino.data.datasource.remote

import com.example.shopino.data.model.ProductDTO
import retrofit2.Response

interface ProductRemoteDataSource {
    suspend fun getProducts(): Response<List<ProductDTO>>
    suspend fun getProductByCategory(category: String): Response<List<ProductDTO>>
    suspend fun searchProducts(query: String): Response<List<ProductDTO>>
    suspend fun getProductById(productId: String): Response<ProductDTO>
}