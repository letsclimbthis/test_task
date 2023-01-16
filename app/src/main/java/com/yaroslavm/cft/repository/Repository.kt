package com.yaroslavm.cft.repository

import kotlinx.coroutines.flow.Flow

interface Repository<I,O> {

    suspend fun get(entity: I): Flow<O>

    suspend fun get1(entity: I): O

    suspend fun getAl(): Flow<List<O>>

    suspend fun deleteAll(): Flow<Unit>

}