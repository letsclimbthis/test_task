package com.yaroslavm.cft.ui.request

import com.yaroslavm.cft.ui.response.BinResponse

sealed class BinInfoRequestUiState {
    object Initial: BinInfoRequestUiState()
    object BinInfoLoading: BinInfoRequestUiState()
    data class BinInfoLoaded(val data: BinResponse): BinInfoRequestUiState()
    object HistoryListChanged : BinInfoRequestUiState()
    data class Error(val message: String): BinInfoRequestUiState()

}

