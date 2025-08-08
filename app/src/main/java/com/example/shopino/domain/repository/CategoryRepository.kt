package com.example.shopino.domain.repository

import com.example.shopino.domain.model.CategoryModel
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<Result<List<CategoryModel>>>
}