package com.yaroslavm.cft.repository

import com.yaroslavm.cft.ui.request.BinRequest
import com.yaroslavm.cft.repository.remote.HttpResponse
import com.yaroslavm.cft.repository.remote.RemoteDataSource
import javax.inject.Inject


class BinInfoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource<BinRequest,HttpResponse,Unit>
): Repository<BinRequest,HttpResponse,Unit> {

    override suspend fun get(key: BinRequest): HttpResponse = remoteDataSource.get(key)

    override suspend fun getAll(): List<HttpResponse> {
        TODO("Not yet implemented")
    }
    override suspend fun saveAll(list: List<Unit>) {
        TODO("Not yet implemented")
    }
    override suspend fun delete(key: BinRequest): Boolean {
        TODO("Not yet implemented")
    }
    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }
}