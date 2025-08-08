package com.example.shopino.domain.repository

import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(query: String): Flow<List<ItemsModel>> {
        return productRepository.searchProducts(query)
    }
}