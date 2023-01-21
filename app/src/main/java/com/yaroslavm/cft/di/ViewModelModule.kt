package com.yaroslavm.cft.di

import com.yaroslavm.cft.ui.request.BinRequest
import com.yaroslavm.cft.repository.*
import com.yaroslavm.cft.repository.local.BinRequestHistoryLocalDataSource
import com.yaroslavm.cft.repository.remote.BinInfoRemoteDataSource
import com.yaroslavm.cft.repository.remote.HttpResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideBinInfoRepository(
        binInfoRemoteDataSource: BinInfoRemoteDataSource
    ): Repository<BinRequest, HttpResponse, Unit> = BinInfoRepository(binInfoRemoteDataSource)


    @Provides
    @ViewModelScoped
    fun provideBinRequestHistoryRepository(
        localDataSource: BinRequestHistoryLocalDataSource
    ): Repository<Unit, BinRequest, BinRequest> = BinRequestHistoryRepository(localDataSource)
}