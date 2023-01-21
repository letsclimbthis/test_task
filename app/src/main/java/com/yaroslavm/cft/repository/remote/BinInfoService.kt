package com.yaroslavm.cft.repository.remote

import com.yaroslavm.cft.ui.response.BinResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BinInfoService {

    @GET("/{bin}")
    suspend fun get(@Path("bin") bin: String): BinResponse?
}