package com.yaroslavm.cft.repository.remote

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<I,O> {
    suspend fun get(entity: I): O
}