package com.yaroslavm.cft.di

import com.yaroslavm.cft.BinEntity
import com.yaroslavm.cft.repository.*
import com.yaroslavm.cft.repository.remote.BinInfoRemoteDataSource
import com.yaroslavm.cft.repository.remote.BinlistResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    // TODO: replace with ViewModel component
    @Provides
    @ViewModelScoped
    fun provideBinInfoRepository(
        binInfoRemoteDataSource: BinInfoRemoteDataSource
    ): Repository<BinEntity, BinlistResponse> = BinInfoRepository(binInfoRemoteDataSource)
}