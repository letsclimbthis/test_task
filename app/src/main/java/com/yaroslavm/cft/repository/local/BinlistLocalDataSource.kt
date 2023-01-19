package com.yaroslavm.cft.repository.local

import com.yaroslavm.cft.BinEntity
import com.yaroslavm.cft.repository.remote.BinlistResponse
import com.yaroslavm.cft.repository.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class BinlistLocalDataSource: RemoteDataSource<BinEntity, BinlistResponse> {

    override suspend fun get(entity: BinEntity): BinlistResponse {
        TODO("Not yet implemented")
    }
}