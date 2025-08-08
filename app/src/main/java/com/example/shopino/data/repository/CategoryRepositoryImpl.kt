package com.example.shopino.data.repository

import com.example.shopino.R // برای دسترسی به R.drawable
import com.example.shopino.data.datasource.remote.CategoryRemoteDataSource
import com.example.shopino.domain.model.CategoryModel
import com.example.shopino.domain.repository.CategoryRepository
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {

    // Helper function to map API category names to local CategoryModel with drawable
    private fun mapApiCategoryNameToModel(apiCategoryName: String): CategoryModel {
        val displayName = apiCategoryName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
        val iconResId = when (apiCategoryName.lowercase()) {
            "electronics" -> R.drawable.category1
            "jewelery" -> R.drawable.category2
            "men's clothing" -> R.drawable.category3
            "women's clothing" -> R.drawable.category4
            else -> R.drawable.placeholder
        }
        return CategoryModel(apiName = apiCategoryName, displayName = displayName, iconResId = iconResId)
    }

    override fun getCategories(): Flow<Result<List<CategoryModel>>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getCategories()
            if (response.isSuccessful) {
                val categoryNames = response.body()
                if (categoryNames != null) {
                    val categoryModels = categoryNames.map { mapApiCategoryNameToModel(it) }
                    emit(Result.Success(categoryModels))
                } else {
                    emit(Result.Error(message = "Category names response body is null"))
                }
            } else {
                emit(Result.Error(message = "API Error for categories: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Result.Error(exception = e, message = "Network error while fetching categories"))
        } catch (e: Exception) {
            emit(Result.Error(exception = e, message = "Unexpected error while fetching categories: ${e.localizedMessage}"))
        }
    }
}
