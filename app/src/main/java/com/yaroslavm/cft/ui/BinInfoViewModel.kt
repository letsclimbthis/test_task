package com.yaroslavm.cft.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaroslavm.cft.BinRequest
import com.yaroslavm.cft.repository.remote.BinlistResponse
import com.yaroslavm.cft.repository.Repository
import com.yaroslavm.cft.ui.request.BinInfoRequestUiState
import com.yaroslavm.cft.ui.response.BinInfoResponseFragment
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
    private val binRequestInfoRepository: Repository<BinRequest, BinlistResponse>
    ): ViewModel(), DefaultLifecycleObserver {

    private val _binInfoRequestUiStateFlow = MutableStateFlow<BinInfoRequestUiState>(
        BinInfoRequestUiState.Initial)
    val binInfoRequestUiStateFlow = _binInfoRequestUiStateFlow.asStateFlow()

    private val _binRequestFlow = MutableStateFlow(BinRequest("", 0L))
    val binRequestFlow = _binRequestFlow.asStateFlow()

    private val _historyList = mutableListOf<BinRequest>()
    val historyList: MutableList<BinRequest> = Collections.unmodifiableList(_historyList)

    private var fetchBinInfo: Job? = null

    fun fetchBinInfo(cardBin: String) {

        _historyList.add(BinRequest(cardBin, System.currentTimeMillis()))

        _binInfoRequestUiStateFlow.value = BinInfoRequestUiState.BinInfoLoading

        fetchBinInfo?.cancel()
        fetchBinInfo = viewModelScope.launch {
            try {
                val binlistRequest = BinRequest(cardBin, System.currentTimeMillis())
                _binRequestFlow.update { binlistRequest }

                val binlistResponse = binRequestInfoRepository.get(binlistRequest)
                _binInfoRequestUiStateFlow.update { BinInfoRequestUiState.BinInfoLoaded(binlistResponse) }

            } catch (ioe: IOException) {
                _binInfoRequestUiStateFlow.update { BinInfoRequestUiState.Error(ioe.message!!) }
            }
        }
    }

    fun fetchRecentBinRequest() {
        val itemList = listOf<BinRequest>(
            BinRequest("45717360", 1674147600000),

//            BinEntity("123456"),
//            BinEntity("654321"),
//            BinEntity("123456"),
//            BinEntity("654321"),
//            BinEntity("123456"),
//            BinEntity("654321"),
//            BinEntity("123456"),
//            BinEntity("654321"),
        )


//        _binHistoryRequestsFlow.update { it.apply { addAll(itemList) } }

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

//    fun updateBinEntity(cardNumber: String) {
//        _binEntityStateFlow.value = BinEntity(cardNumber)
//    }

}