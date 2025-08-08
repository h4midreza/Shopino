package com.example.shopino.data.datasource.remote

import retrofit2.Response

interface CategoryRemoteDataSource {
    suspend fun getCategories(): Response<List<String>>
}
