package com.yaroslavm.cft.repository

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<I,O> {

    suspend fun get(entity: I): Flow<O>
    suspend fun get1(entity: I): O
}