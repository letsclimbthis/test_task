package com.yaroslavm.cft.repository

import com.yaroslavm.cft.BinEntity
import com.yaroslavm.cft.repository.remote.BinlistResponse
import com.yaroslavm.cft.repository.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BinInfoRepository @Inject constructor(
    private val binEntityInfoRemoteDataSource: RemoteDataSource<BinEntity, BinlistResponse>
): Repository<BinEntity, BinlistResponse> {

    override suspend fun get(entity: BinEntity): BinlistResponse = binEntityInfoRemoteDataSource.get(entity)

    override suspend fun getAl(): List<BinlistResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Unit {
        TODO("Not yet implemented")
    }
}