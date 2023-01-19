package com.yaroslavm.cft.repository

import kotlinx.coroutines.flow.Flow

interface Repository<I,O> {

    suspend fun get(entity: I): O

    suspend fun getAl(): List<O>

    suspend fun deleteAll(): Unit

}