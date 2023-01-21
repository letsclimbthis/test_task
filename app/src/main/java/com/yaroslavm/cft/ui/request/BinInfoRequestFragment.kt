package com.yaroslavm.cft.ui.request

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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
    private val self = this

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
        binInfoViewModel.fetchRecentBinRequest()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(p0: View?) {

        when (p0?.tag) {
            "button_send_request" -> {
                val cardNumber =
                    requireView().
                    findViewById<EditText>(R.id.enter_bin_edit_text).
                    text.toString()

                if (cardNumber != "") {
                    binInfoViewModel.fetchBinInfo(cardNumber)
                } else {
                    makeToast(resources.getString(R.string.fill_search_input))
                }

            }

            "delete" -> {
                binInfoViewModel.clearRecentBinRequest()
                binding.
                recyclerViewRequestHistory.
                adapter?.
                notifyDataSetChanged()

            }
        }
    }

    override fun onItemListClick(viewClicked: View, itemIndexInList: Int) {
        val cardNumber = binInfoViewModel.historyList[itemIndexInList]
        binding.enterBinEditText.setText(cardNumber.value)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun enableListeningBinInfoUiStateChanged() {
        val navController = findNavController(this)
        lifecycleScope.launch {
            binInfoViewModel.binInfoRequestUiStateFlow.collect {
                when (it) {

                    is BinInfoRequestUiState.Initial -> {
                        if (self::binding.isInitialized)
                            binding.apply {
                                progressBar.visibility = View.GONE
                                buttonSendRequest.visibility = View.VISIBLE
                                recyclerViewRequestHistory.visibility = View.VISIBLE
                            }
                    }

                    is BinInfoRequestUiState.BinInfoLoading -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            buttonSendRequest.visibility = View.GONE
                            recyclerViewRequestHistory.visibility = View.GONE
                        }
                    }

                    is BinInfoRequestUiState.BinInfoLoaded -> {
                        navController.navigate(R.id.action_bin_info_request_fragment_to_bin_info_response_fragment)
                    }

                    is BinInfoRequestUiState.HistoryListChanged -> {
                        if (self::binding.isInitialized) {
                            binding.
                            recyclerViewRequestHistory.
                            adapter?.
                            notifyDataSetChanged()
                        }
                    }

                    is BinInfoRequestUiState.Error -> {
                        makeToast(it.message)
                        binInfoViewModel.onRequestFragmentEvent(BinInfoRequestFragmentEvent.ErrorResponseConsumed)
                    }
                }
            }
        }

    }

    private fun makeToast(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStop() {
        super.onStop()
        binInfoViewModel.saveRecentBinRequest()
    }

}
