package com.example.shopino.di

import com.example.shopino.data.datasource.remote.CategoryRemoteDataSource
import com.example.shopino.data.datasource.remote.ProductRemoteDataSource
import com.example.shopino.data.repository.CategoryRepositoryImpl
import com.example.shopino.domain.repository.ProductRepository
import com.example.shopino.data.repository.ProductRepositoryImpl
import com.example.shopino.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        remoteDataSource: ProductRemoteDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(remoteDataSource: CategoryRemoteDataSource): CategoryRepository {
        return CategoryRepositoryImpl(remoteDataSource)
    }
}