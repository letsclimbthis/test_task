package com.yaroslavm.cft.ui

import androidx.lifecycle.*
import com.yaroslavm.cft.ui.request.BinRequest
import com.yaroslavm.cft.repository.Repository
import com.yaroslavm.cft.ui.response.BinResponse
import com.yaroslavm.cft.repository.remote.HttpResponse
import com.yaroslavm.cft.ui.request.BinInfoRequestFragmentEvent
import com.yaroslavm.cft.ui.request.BinInfoRequestUiState
import com.yaroslavm.cft.ui.response.BinInfoResponseFragmentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BinInfoViewModel @Inject constructor(
    private val binInfoResponseRepository: Repository<BinRequest, HttpResponse, Unit>,
    private val binRequestHistoryRepository: Repository<Unit, BinRequest, BinRequest>,
    ):
    ViewModel()
{

    private val _binInfoRequestUiStateFlow = MutableStateFlow<BinInfoRequestUiState>(
        BinInfoRequestUiState.Initial)
    val binInfoRequestUiStateFlow = _binInfoRequestUiStateFlow.asStateFlow()

    private val _binRequestFlow = MutableStateFlow(BinRequest("", 0L))
    val binRequestFlow = _binRequestFlow.asStateFlow()

    private val _historyList = mutableListOf<BinRequest>()
    val historyList: MutableList<BinRequest> = Collections.unmodifiableList(_historyList)

    private var binInfoIO: Job? = null

    fun fetchBinInfo(cardBin: String) {
        _historyList.add(BinRequest(cardBin, System.currentTimeMillis()))
        _binInfoRequestUiStateFlow.update { BinInfoRequestUiState.BinInfoLoading }

        binInfoIO?.cancel()
        binInfoIO = viewModelScope.launch(Dispatchers.IO) {

            val binlistRequest = BinRequest(cardBin, System.currentTimeMillis())
            _binRequestFlow.update { binlistRequest }

            when(val response = binInfoResponseRepository.get(binlistRequest)) {

                is HttpResponse.Success<*> -> {
                    _binInfoRequestUiStateFlow.update {
                        BinInfoRequestUiState.BinInfoLoaded(response.value as BinResponse)
                    }
                }

                is HttpResponse.Error<*> -> {
                    _binInfoRequestUiStateFlow.update {
                        BinInfoRequestUiState.Error((response.value as java.lang.Exception).toString())
                    }
                }

            }
        }
    }

    private var requestHistoryIO: Job? = null

    fun fetchRecentBinRequest() {
        requestHistoryIO?.cancel()
        requestHistoryIO = viewModelScope.launch() {
            try {
                val itemList = binRequestHistoryRepository.getAll()
                if(itemList.isNotEmpty()) {
                    _historyList.clear()
                    _historyList.addAll(itemList)
                    _binInfoRequestUiStateFlow.update {
                        BinInfoRequestUiState.HistoryListChanged
                    }
                }
            } catch (ioe: IOException) {
                _binInfoRequestUiStateFlow.update { BinInfoRequestUiState.Error(ioe.message!!) }
            }
        }
    }

    fun saveRecentBinRequest() {
        requestHistoryIO?.cancel()
        requestHistoryIO = viewModelScope.launch(Dispatchers.IO) {
            try {
                binRequestHistoryRepository.saveAll(_historyList
//                    .apply { clear() }
                )

            } catch (ioe: IOException) {
                _binInfoRequestUiStateFlow.update { BinInfoRequestUiState.Error(ioe.message!!) }
            }
        }
    }

    fun clearRecentBinRequest() {
        _historyList.clear()
        _binInfoRequestUiStateFlow.update {
            BinInfoRequestUiState.HistoryListChanged
            BinInfoRequestUiState.Initial}
    }

    fun onResponseFragmentEvent(event: BinInfoResponseFragmentEvent) {
        when(event) {
            is BinInfoResponseFragmentEvent.BackButtonPressed ->
                _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Initial
        }
    }

    fun onRequestFragmentEvent(event: BinInfoRequestFragmentEvent) {
        when(event) {
            is BinInfoRequestFragmentEvent.ErrorResponseConsumed ->
                _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.Initial
        }
    }

}