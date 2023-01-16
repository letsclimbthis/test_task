package com.yaroslavm.cft.repository

import retrofit2.http.GET
import retrofit2.http.Path

interface BinInfoService {

    @GET("/{bin}")
    suspend fun get(@Path("bin") bin: String): BinlistResponse
}