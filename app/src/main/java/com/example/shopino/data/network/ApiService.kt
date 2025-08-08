package com.example.shopino.data.network

import com.example.shopino.data.model.ProductDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductDTO>>

    @GET("products/category/{categoryName}")
    suspend fun getProductByCategory(@Path("categoryName") categoryName: String): Response<List<ProductDTO>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: String): Response<ProductDTO>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<List<ProductDTO>>
}
