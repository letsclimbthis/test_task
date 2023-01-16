package com.yaroslavm.cft.repository

import com.yaroslavm.cft.BinEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BinInfoRepository @Inject constructor(
    private val binEntityInfoRemoteDataSource: RemoteDataSource<BinEntity,BinlistResponse>
): Repository<BinEntity,BinlistResponse> {

    override suspend fun get(entity: BinEntity): Flow<BinlistResponse> = binEntityInfoRemoteDataSource.get(entity)

    override suspend fun get1(entity: BinEntity): BinlistResponse = binEntityInfoRemoteDataSource.get1(entity)

    override suspend fun getAl(): Flow<List<BinlistResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Flow<Unit> {
        TODO("Not yet implemented")
    }
}