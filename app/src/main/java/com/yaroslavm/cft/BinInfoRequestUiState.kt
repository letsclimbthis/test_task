package com.yaroslavm.cft

import com.yaroslavm.cft.repository.BinlistResponse

sealed class BinInfoRequestUiState {
    object Initial: BinInfoRequestUiState()
    object Loading: BinInfoRequestUiState()
    data class Loaded(val data: BinlistResponse): BinInfoRequestUiState()
    data class Error(val message: String): BinInfoRequestUiState()
}

