package com.yaroslavm.cft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BinInfoRequestFragment:
    Fragment(),
    View.OnClickListener
{

    val binInfoViewModel: BinInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bin_info_request_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            // TODO: consider what to use `collectOnLifecycle` or `repeatOnLifecycle`
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                binInfoViewModel.requestFragmentUiStateFlow.collect {

                    when (it) {
                        is RequestFragmentUiState.Loaded -> {
                            Toast.makeText(requireContext(), it.data.country?.name, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
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

                binInfoViewModel.fetchBinInfo("45717360")

//                val navController = findNavController(this)
//                navController.navigate(R.id.action_request_fragment_to_fragment2)
            }
        }
    }


//    private val viewModel: NewsViewModel by viewModels()
//


}