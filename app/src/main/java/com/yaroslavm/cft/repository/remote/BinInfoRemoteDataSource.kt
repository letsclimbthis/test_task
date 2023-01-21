package com.yaroslavm.cft.repository.remote

import com.yaroslavm.cft.ui.request.BinRequest
import javax.inject.Inject

class BinInfoRemoteDataSource @Inject constructor(
    private val binInfoService: BinInfoService
) : RemoteDataSource<BinRequest,HttpResponse,Unit> {

    override suspend fun get(key: BinRequest): HttpResponse {
        var response: HttpResponse
        try {
            response = HttpResponse.Success(binInfoService.get(key.value))
        } catch (e: Exception) {
            println(e)
            response = HttpResponse.Error(e)
        }
        return response
    }

    override suspend fun getAll(): List<HttpResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(key: BinRequest): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(list: List<Unit>) {
        TODO("Not yet implemented")
    }

}