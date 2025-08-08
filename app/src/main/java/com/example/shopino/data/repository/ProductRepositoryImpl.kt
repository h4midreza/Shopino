package com.example.shopino.data.repository

import com.example.shopino.data.datasource.remote.ProductRemoteDataSource
import com.example.shopino.data.mapper.toDomainModel
import com.example.shopino.data.mapper.toDomainModelList
import com.example.shopino.domain.model.ItemsModel
import com.example.shopino.domain.repository.ProductRepository
import com.example.shopino.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override fun getProducts(): Flow<Result<List<ItemsModel>>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getProducts()
            if (response.isSuccessful) {
                val productDTOs = response.body()
                if (productDTOs != null) {
                    emit(Result.Success(productDTOs.toDomainModelList()))
                } else {
                    emit(Result.Error(message = "Response body is null for all products"))
                }
            } else {
                emit(Result.Error(message = "API Error for all products: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Result.Error(exception = e, message = "Network error for all products or no internet connection"))
        } catch (e: Exception) {
            emit(Result.Error(exception = e, message = "An unexpected error occurred for all products: ${e.localizedMessage}"))
        }
    }

    override fun getProductByCategory(category: String): Flow<Result<List<ItemsModel>>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getProductByCategory(category)
            if (response.isSuccessful) {
                val productDTOs = response.body()
                if (productDTOs != null) {
                    emit(Result.Success(productDTOs.toDomainModelList()))
                } else {
                    emit(Result.Error(message = "Response body is null for category: $category"))
                }
            } else {
                emit(Result.Error(message = "API Error for category $category: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Result.Error(exception = e, message = "Network error for category $category or no internet connection"))
        } catch (e: Exception) {
            emit(Result.Error(exception = e, message = "An unexpected error occurred for category $category: ${e.localizedMessage}"))
        }
    }

    override fun searchProducts(query: String): Flow<List<ItemsModel>> = flow {
        if (query.isBlank()) {
            emit(emptyList())
            return@flow
        }
        try {
            val response = remoteDataSource.getProducts()
            if (response.isSuccessful) {
                val productDTOs = response.body()
                if (productDTOs != null) {
                    val allProducts = productDTOs.toDomainModelList()
                    // Filter in-memory
                    val filteredList = allProducts.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                    emit(filteredList)
                } else {
                    emit(emptyList())
                }
            } else {
                emit(emptyList())
            }
        } catch (e: IOException) {
            emit(emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getProductById(productId: String): Flow<Result<ItemsModel>> = flow {
        emit(Result.Loading)
        try {
            val response = remoteDataSource.getProductById(productId)
            if (response.isSuccessful) {
                val productDTOs = response.body()
                if (productDTOs != null) {
                    emit(Result.Success(productDTOs.toDomainModel()))
                } else {
                    emit(Result.Error(message = "Response body is null for productId: $productId"))
                }
            } else {
                emit(Result.Error(message = "API Error for productId $productId: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Result.Error(exception = e, message = "Network error for productId $productId or no internet connection"))
        } catch (e: Exception) {
            emit(Result.Error(exception = e, message = "An unexpected error occurred for productId $productId: ${e.localizedMessage}"))
        }
    }
}
