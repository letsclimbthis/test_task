package com.yaroslavm.cft.repository

import com.yaroslavm.cft.BinEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BinInfoRemoteDataSource @Inject constructor(
    private val binInfoService: BinInfoService
) : RemoteDataSource<BinEntity,BinlistResponse> {

    override suspend fun get(entity: BinEntity): Flow<BinlistResponse> = flow {
//        binlistLocalDataSource.saveBin(bin)
        emit(binInfoService.get(entity.value))
    }

    override suspend fun get1(entity: BinEntity): BinlistResponse = binInfoService.get(entity.value)

}