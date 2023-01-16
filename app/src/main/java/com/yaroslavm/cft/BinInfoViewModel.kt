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

    private val _requestFragmentUiStateFlow = MutableStateFlow<RequestFragmentUiState>(RequestFragmentUiState.Initial)
    val requestFragmentUiStateFlow = _requestFragmentUiStateFlow.asStateFlow()

    private var fetchBinInfo: Job? = null

    fun fetchBinInfo(bin: String) {

        _requestFragmentUiStateFlow.value = RequestFragmentUiState.Loading

        fetchBinInfo?.cancel()
        fetchBinInfo = viewModelScope.launch {

            try {
                val binlistResponse = binEntityInfoRepository.get1(BinEntity(bin))
                _requestFragmentUiStateFlow.update {
                    RequestFragmentUiState.Loaded(data = binlistResponse)
                }

            } catch (ioe: IOException) {
                _requestFragmentUiStateFlow.update {
                    RequestFragmentUiState.Error(message = ioe.message!!)
                }
            }
        }
    }


}