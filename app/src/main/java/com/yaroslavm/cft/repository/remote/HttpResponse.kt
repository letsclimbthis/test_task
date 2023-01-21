package com.yaroslavm.cft.repository.remote

sealed class HttpResponse {
    data class Success<V>(val value: V) : HttpResponse()
    data class Error<V>(val value: V) : HttpResponse()
}