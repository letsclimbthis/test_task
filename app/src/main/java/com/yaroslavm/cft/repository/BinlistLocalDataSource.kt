package com.yaroslavm.cft.repository

import com.yaroslavm.cft.BinEntity
import kotlinx.coroutines.flow.Flow

class BinlistLocalDataSource: RemoteDataSource<BinEntity,BinlistResponse> {

    override suspend fun get(entity: BinEntity): Flow<BinlistResponse> {
        TODO("Not yet implemented")
    }
    override suspend fun get1(entity: BinEntity): BinlistResponse {
        TODO("Not yet implemented")
    }
}