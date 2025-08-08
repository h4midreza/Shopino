package com.example.shopino.data.datasource.remote

import com.example.shopino.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRemoteDataSource {
    override suspend fun getCategories(): Response<List<String>> {
        return apiService.getCategories()
    }
}