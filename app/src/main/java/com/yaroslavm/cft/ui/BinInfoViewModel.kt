package com.yaroslavm.cft.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaroslavm.cft.BinEntity
import com.yaroslavm.cft.repository.remote.BinlistResponse
import com.yaroslavm.cft.repository.Repository
import com.yaroslavm.cft.ui.response.BinInfoResponseFragment
import com.yaroslavm.cft.ui.response.BinInfoResponseFragmentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BinInfoViewModel @Inject constructor(
    private val binEntityInfoRepository: Repository<BinEntity, BinlistResponse>
    ): ViewModel(), DefaultLifecycleObserver {

    private val _binInfoRequestUiStateFlow = MutableStateFlow<BinInfoRequestUiState>(
        BinInfoRequestUiState.Initial)
    val binInfoRequestUiStateFlow = _binInfoRequestUiStateFlow.asStateFlow()

    private val _binEntityStateFlow = MutableStateFlow(BinEntity(""))
    val binEntityStateFlow = _binEntityStateFlow.asStateFlow()

    private var fetchBinInfo: Job? = null

    fun fetchBinInfo(bin: String) {
        _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Loading
        fetchBinInfo?.cancel()
        fetchBinInfo = viewModelScope.launch {
            try {
                val binlistResponse = binEntityInfoRepository.get(BinEntity(bin))
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

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        when (owner) {
            is BinInfoResponseFragment ->
                _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Initial
        }
    }

    fun onResponseFragmentEvent(event: BinInfoResponseFragmentEvent) {
        when(event) {
            is BinInfoResponseFragmentEvent.BackButtonPressed ->
                _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Initial
        }
    }

    fun updateBinEntity(cardNumber: String) {
        _binEntityStateFlow.value = BinEntity(cardNumber)
    }

}