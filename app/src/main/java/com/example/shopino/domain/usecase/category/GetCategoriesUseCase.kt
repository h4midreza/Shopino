package com.example.shopino.domain.usecase.category

import com.example.shopino.domain.repository.CategoryRepository
import com.example.shopino.domain.model.CategoryModel
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<Result<List<CategoryModel>>> {
        return categoryRepository.getCategories()
    }
}