package com.yaroslavm.cft.di

import com.google.gson.Gson
import com.yaroslavm.cft.BinRequest
import com.yaroslavm.cft.repository.*
import com.yaroslavm.cft.repository.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideJsonConverter(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(ServiceAPIConfig.url)
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun provideBinInfoService(
        retrofit: Retrofit
    ): BinInfoService = retrofit.create(BinInfoService::class.java)

    @Provides
    @Singleton
    fun bindBinInfoRemoteDataSource(
        binInfoService: BinInfoService
    ): RemoteDataSource<BinRequest, BinlistResponse> = BinInfoRemoteDataSource(binInfoService)

}