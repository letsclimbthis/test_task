package com.yaroslavm.cft.repository.local

import com.yaroslavm.cft.BinRequest
import com.yaroslavm.cft.repository.remote.BinlistResponse
import com.yaroslavm.cft.repository.remote.RemoteDataSource

class BinlistLocalDataSource: RemoteDataSource<BinRequest, BinlistResponse> {

    override suspend fun get(entity: BinRequest): BinlistResponse {
        TODO("Not yet implemented")
    }
}