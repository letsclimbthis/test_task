package com.yaroslavm.cft.repository.remote

import com.yaroslavm.cft.BinRequest
import javax.inject.Inject

class BinInfoRemoteDataSource @Inject constructor(
    private val binInfoService: BinInfoService
) : RemoteDataSource<BinRequest, BinlistResponse> {

    override suspend fun get(entity: BinRequest): BinlistResponse = binInfoService.get(entity.value)

}