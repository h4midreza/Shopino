package com.example.shopino.domain.usecase.product

import com.example.shopino.domain.repository.ProductRepository
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetProductByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(categoryName: String): Flow<Result<List<ItemsModel>>> {
        if (categoryName.isBlank()) {
             return flowOf(Result.Error(message = "Category name cannot be blank"))
        }
        return productRepository.getProductByCategory(categoryName)
    }
}