package com.example.shopino.domain.usecase.product

import com.example.shopino.domain.repository.ProductRepository
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<Result<List<ItemsModel>>> {
        return productRepository.getProducts()
    }
}