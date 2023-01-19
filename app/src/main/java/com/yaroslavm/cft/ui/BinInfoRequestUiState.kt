package com.yaroslavm.cft.ui

import com.yaroslavm.cft.repository.remote.BinlistResponse

sealed class BinInfoRequestUiState {
    object Initial: BinInfoRequestUiState()
    object Loading: BinInfoRequestUiState()
    data class Loaded(val data: BinlistResponse): BinInfoRequestUiState()
    data class Error(val message: String): BinInfoRequestUiState()
}

