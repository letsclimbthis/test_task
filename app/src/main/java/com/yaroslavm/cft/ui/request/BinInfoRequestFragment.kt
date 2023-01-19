package com.yaroslavm.cft.ui.request

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.yaroslavm.cft.BinEntity
import com.yaroslavm.cft.R
import com.yaroslavm.cft.ui.BinInfoRequestUiState
import com.yaroslavm.cft.ui.BinInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BinInfoRequestFragment:
    Fragment(),
    View.OnClickListener
{

    private val binInfoViewModel: BinInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bin_info_request_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableListeningBinInfoUiStateChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewComponents(view)
    }

    private fun initViewComponents(view: View) {
        view.findViewWithTag<Button>("request_fragment_button_send_request").also {
            it.setOnClickListener(this)
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.tag) {
            "request_fragment_button_send_request" -> {
                val cardNumber =
                    requireView().
                    findViewById<EditText>(R.id.request_fragment_edit_text_enter_bin).
                    text.toString()
                binInfoViewModel.apply {
                    fetchBinInfo(cardNumber)
                    updateBinEntity(cardNumber)
                }
            }
        }
    }

    private fun enableListeningBinInfoUiStateChanged() {
        val navController = findNavController(this)

        lifecycleScope.launch {
            // TODO: consider what to use `collectOnLifecycle` or `repeatOnLifecycle`
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                binInfoViewModel.binInfoRequestUiStateFlow.collect {
                    when (it) {

                        is BinInfoRequestUiState.Initial -> { }

                        is BinInfoRequestUiState.Loading -> {
                            //    textViewMessage.visibility = View.GONE
                            //    progressBar.visibility = View.VISIBLE
                        }

                        is BinInfoRequestUiState.Loaded -> {
                            navController.navigate(R.id.action_bin_info_request_fragment_to_bin_info_response_fragment)
                        }

                        is BinInfoRequestUiState.Error -> { }
                    }
                }
            }
        }
    }

}