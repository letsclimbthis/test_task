package com.yaroslavm.cft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaroslavm.cft.repository.BinlistResponse
import com.yaroslavm.cft.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BinInfoViewModel @Inject constructor(
    private val binEntityInfoRepository: Repository<BinEntity,BinlistResponse>
    ): ViewModel() {

    private val _binInfoRequestUiStateFlow = MutableStateFlow<BinInfoRequestUiState>(BinInfoRequestUiState.Initial)
    val binInfoRequestUiStateFlow = _binInfoRequestUiStateFlow.asStateFlow()

    private var fetchBinInfo: Job? = null

    fun fetchBinInfo(bin: String) {

        _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Loading

        fetchBinInfo?.cancel()
        fetchBinInfo = viewModelScope.launch {
            try {
                val binlistResponse = binEntityInfoRepository.get1(BinEntity(bin))
                _binInfoRequestUiStateFlow.update {
                    BinInfoRequestUiState.Loaded(data = binlistResponse)
                }
            } catch (ioe: IOException) {
                _binInfoRequestUiStateFlow.update {
                    BinInfoRequestUiState.Error(message = ioe.message!!)
                }
            }
        }
    }

    fun setBinInfoRequestUiStateAsInitial() {
        _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Initial
    }

}