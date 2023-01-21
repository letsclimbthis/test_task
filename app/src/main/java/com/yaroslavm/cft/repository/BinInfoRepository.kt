package com.yaroslavm.cft.repository

import com.yaroslavm.cft.BinRequest
import com.yaroslavm.cft.repository.remote.BinlistResponse
import com.yaroslavm.cft.repository.remote.RemoteDataSource
import javax.inject.Inject


class BinInfoRepository @Inject constructor(
    private val binRequestInfoRemoteDataSource: RemoteDataSource<BinRequest, BinlistResponse>
): Repository<BinRequest, BinlistResponse> {

    override suspend fun get(entity: BinRequest): BinlistResponse = binRequestInfoRemoteDataSource.get(entity)

    override suspend fun getAl(): List<BinlistResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Unit {
        TODO("Not yet implemented")
    }
}