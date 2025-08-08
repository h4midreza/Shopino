package com.example.shopino.di

import com.example.shopino.data.datasource.remote.CategoryRemoteDataSource
import com.example.shopino.data.datasource.remote.CategoryRemoteDataSourceImpl
import com.example.shopino.data.datasource.remote.ProductRemoteDataSource
import com.example.shopino.data.datasource.remote.ProductRemoteDataSourceImpl
import com.example.shopino.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://fakestoreapi.com/"
    private const val TIMEOUT_SECONDS = 30L

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRemoteDataSource(apiService: ApiService): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(apiService: ApiService): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(apiService)
    }
}
