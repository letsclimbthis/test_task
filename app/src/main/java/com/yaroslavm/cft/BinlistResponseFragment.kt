package com.yaroslavm.cft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BinlistResponseFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bin_info_response_fragment, container, false)
    }

//    private val viewModel: NewsViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        ...
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect {
//                    // Update UI elements
//                }
//            }
//        }
//    }

//    private fun subscribeUi() {
//
//        viewModelMineFieldCommands.observe(viewLifecycleOwner) { viewModelMineFieldCommand ->
//            log("subscribeUi(): view model command = $viewModelMineFieldCommand")
//            if (viewModelMineFieldCommand != null)
//                executeAppropriateAction(viewModelMineFieldCommand)
//        }
//    }



}