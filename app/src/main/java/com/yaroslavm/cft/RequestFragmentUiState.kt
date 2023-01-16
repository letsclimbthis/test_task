package com.yaroslavm.cft

import com.yaroslavm.cft.repository.BinlistResponse

sealed class RequestFragmentUiState {
    object Initial: RequestFragmentUiState()
    object Loading: RequestFragmentUiState()
    data class Loaded(val data: BinlistResponse): RequestFragmentUiState()
    data class Error(val message: String): RequestFragmentUiState()
}

