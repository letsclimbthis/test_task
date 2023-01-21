package com.yaroslavm.cft.repository.local

interface LocalDataSource <K,O,I> {

    suspend fun get(key: K): O
    suspend fun getAll(): List<O>
    suspend fun saveAll(list: List<I>)
    suspend fun delete(key: K): Boolean
    suspend fun deleteAll(): Boolean

}