package com.yaroslavm.cft.repository

import com.yaroslavm.cft.ui.request.BinRequest
import com.yaroslavm.cft.repository.local.LocalDataSource
import javax.inject.Inject


class BinRequestHistoryRepository @Inject constructor(
    private val localDataSource: LocalDataSource<Unit, BinRequest, BinRequest>
): Repository<Unit, BinRequest, BinRequest> {

    override suspend fun get(key: Unit): BinRequest {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<BinRequest> {
        return localDataSource.getAll()
    }

    override suspend fun saveAll(list: List<BinRequest>) {
        return localDataSource.saveAll(list)
    }

    override suspend fun delete(key: Unit): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }
}