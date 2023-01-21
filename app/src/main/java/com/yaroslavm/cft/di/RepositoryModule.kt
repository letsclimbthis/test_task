package com.yaroslavm.cft.di

import android.content.Context
import com.google.gson.Gson
import com.yaroslavm.cft.ui.request.BinRequest
import com.yaroslavm.cft.repository.*
import com.yaroslavm.cft.repository.local.BinRequestHistoryLocalDataSource
import com.yaroslavm.cft.repository.local.LocalDataSource
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
object RepositoryModule {

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
    ): RemoteDataSource<BinRequest, HttpResponse, Unit> = BinInfoRemoteDataSource(binInfoService)



    @Provides
    @Singleton
    fun bindRequestHistoryLocalDataSource(
        appContext: Context,
        gson: Gson
    ): LocalDataSource<Unit, BinRequest, BinRequest> = BinRequestHistoryLocalDataSource(appContext, gson)

}