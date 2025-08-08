package com.example.shopino.domain.repository

import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<Result<List<ItemsModel>>>
    fun getProductByCategory(category: String): Flow<Result<List<ItemsModel>>>
    fun searchProducts(query: String): Flow<List<ItemsModel>>
    fun getProductById(productId: String): Flow<Result<ItemsModel>>
}
