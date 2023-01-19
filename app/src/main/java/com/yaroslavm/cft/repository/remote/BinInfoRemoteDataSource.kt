package com.yaroslavm.cft.repository.remote

import com.yaroslavm.cft.BinEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BinInfoRemoteDataSource @Inject constructor(
    private val binInfoService: BinInfoService
) : RemoteDataSource<BinEntity, BinlistResponse> {

    override suspend fun get(entity: BinEntity): BinlistResponse = binInfoService.get(entity.value)

}