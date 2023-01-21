package com.yaroslavm.cft.ui.request

import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yaroslavm.cft.R
import com.yaroslavm.cft.databinding.BinInfoRequestFragmentBinding
import com.yaroslavm.cft.ui.BinInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BinInfoRequestFragment:
    Fragment(),
    OnClickListener,
    AdapterSearchHistory.OnHistoryListItemClickListener
{

    private val binInfoViewModel: BinInfoViewModel by activityViewModels()
    private lateinit var binding: BinInfoRequestFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableListeningBinInfoUiStateChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bin_info_request_fragment,
            container,
            false
        )
        binding.clickHandler = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewComponents()
    }

    override fun onStart() {
        super.onStart()
        binding
            .recyclerViewRequestHistory
            .adapter
            ?.notifyItemInserted(binInfoViewModel.historyList.size - 1)
    }

    private fun setViewComponents() {
        val clickHandler = this
        binding.recyclerViewRequestHistory.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = AdapterSearchHistory(binInfoViewModel.historyList, clickHandler)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.tag) {
            "request_fragment_button_send_request" -> {
                val cardNumber =
                    requireView().
                    findViewById<EditText>(R.id.enter_bin_edit_text).
                    text.toString()
                binInfoViewModel.fetchBinInfo(cardNumber)            }
        }
    }

    override fun onItemListClick(viewClicked: View, itemIndexInList: Int) {
        val cardNumber = binInfoViewModel.historyList[itemIndexInList]
        binding.enterBinEditText.setText(cardNumber.value)
    }

    private fun enableListeningBinInfoUiStateChanged() {
        val navController = findNavController(this)
        lifecycleScope.launch {
            binInfoViewModel.binInfoRequestUiStateFlow.collect {
                when (it) {

                    is BinInfoRequestUiState.Initial -> { }

                    is BinInfoRequestUiState.BinInfoLoading -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
//                            requestFragmentEditTextEnterBin.visibility = View.GONE
                            buttonSendRequest.visibility = View.GONE
                            recyclerViewRequestHistory.visibility = View.GONE
                        }
                    }

                    is BinInfoRequestUiState.BinInfoLoaded -> {
                        navController.navigate(R.id.action_bin_info_request_fragment_to_bin_info_response_fragment)
                    }

                    is BinInfoRequestUiState.HistoryListItemAdded -> {
                        binding.
                        recyclerViewRequestHistory.
                        adapter?.
                        notifyItemInserted(it.data)
                    }

                    is BinInfoRequestUiState.HistoryListItemDeleted -> {
                        binding.
                       recyclerViewRequestHistory.
                        adapter?.
                        notifyItemRemoved(it.data)
                    }

                    is BinInfoRequestUiState.Error -> { }
                }
            }
        }
    }

//    private fun enableListeningBinInfoUiStateChanged() {
//        val navController = findNavController(this)
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) { // deleted line
//                binInfoViewModel.binInfoRequestUiStateFlow.collect {
//                    when (it) {
//
//                        is BinInfoRequestUiState.Initial -> { }
//
//                        is BinInfoRequestUiState.BinInfoLoading -> {
//                            binding.apply {
//                                requestFragmentProgressBar.visibility = View.VISIBLE
//                                requestFragmentEditTextEnterBin.visibility = View.GONE
//                                requestFragmentButtonSendRequest.visibility = View.GONE
//                                requestFragmentRecyclerViewRequestHistory.visibility = View.GONE
//                            }
//                        }
//
//                        is BinInfoRequestUiState.BinInfoLoaded -> {
//                            navController.navigate(R.id.action_bin_info_request_fragment_to_bin_info_response_fragment)
//                        }
//
//                        is BinInfoRequestUiState.HistoryListItemAdded -> { }
//
//                        is BinInfoRequestUiState.HistoryListItemDeleted -> { }
//
//                        is BinInfoRequestUiState.Error -> { }
//                    }
//                }
//            }
//        }
//    }


//    private fun subscribeOnBinResponseHistoryChanged() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                binInfoViewModel.binHistoryRequestsFlow.collect {
//                    binding.binRequest = it
//                }
//            }
//        }
//    }
//


}