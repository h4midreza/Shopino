package com.example.shopino.data.datasource.remote

import com.example.shopino.data.model.ProductDTO
import com.example.shopino.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRemoteDataSource {

    override suspend fun getProducts(): Response<List<ProductDTO>> {
        return apiService.getProducts()
    }

    override suspend fun getProductByCategory(category: String): Response<List<ProductDTO>> {
        return apiService.getProductByCategory(category)
    }

    override suspend fun searchProducts(query: String): Response<List<ProductDTO>> {
        return apiService.searchProducts(query)
    }

    override suspend fun getProductById(productId: String): Response<ProductDTO> {
        return apiService.getProductById(productId)
    }
}