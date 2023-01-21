package com.yaroslavm.cft.ui.request

import com.yaroslavm.cft.repository.remote.BinlistResponse

sealed class BinInfoRequestUiState {
    object Initial: BinInfoRequestUiState()
    object BinInfoLoading: BinInfoRequestUiState()
    data class BinInfoLoaded(val data: BinlistResponse): BinInfoRequestUiState()
    data class HistoryListItemAdded(val data: Int): BinInfoRequestUiState()
    data class HistoryListItemDeleted(val data: Int): BinInfoRequestUiState()
    data class Error(val message: String): BinInfoRequestUiState()

}

