package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.Constants
import com.example.data.network.Api
import com.example.data.network.ApiService
import com.example.data.network.ApiServiceImpl
import com.example.data.repository.Repository
import com.example.data.repository.RepositoryImpl
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
object DataModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BASIC)
                    }
                }
            )
            .connectTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS)
            .callTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Api {

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideApiService(api: Api): ApiService = ApiServiceImpl(api)

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService
    ): Repository = RepositoryImpl(apiService)

}