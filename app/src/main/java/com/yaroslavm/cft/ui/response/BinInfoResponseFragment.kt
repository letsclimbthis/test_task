package com.yaroslavm.cft.ui.response

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.TooltipCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yaroslavm.cft.R
import com.yaroslavm.cft.databinding.BinInfoResponseFragmentBinding
import com.yaroslavm.cft.ui.BinInfoRequestUiState
import com.yaroslavm.cft.ui.BinInfoViewModel
import kotlinx.coroutines.launch

class BinInfoResponseFragment:
    Fragment(),
    LifecycleOwner,
    OnClickListener
{

    private val binInfoViewModel: BinInfoViewModel by activityViewModels()
    private var binding: BinInfoResponseFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bin_info_response_fragment,
            container,
            false
        )
        binding?.clickHandler = this
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnBinInfoUiStateChanged()
        subscribeOnBinEntityChanged()
        registerOnBackPressedCallback()
    }

    private fun subscribeOnBinInfoUiStateChanged() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binInfoViewModel.binInfoRequestUiStateFlow.collect {
                    when (it) {
                        is BinInfoRequestUiState.Initial -> { }
                        is BinInfoRequestUiState.Loading -> { }
                        is BinInfoRequestUiState.Loaded -> { binding?.response = it.data }
                        is BinInfoRequestUiState.Error -> { }
                    }
                }
            }
        }
    }

    private fun subscribeOnBinEntityChanged() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binInfoViewModel.binEntityStateFlow.collect {
                    binding?.binEntity = it
                }
            }
        }
    }

//    private fun registerClickListenerForViews() {
//        val listener = this
//        binding?.apply {
//            listOf(
//                textViewBankNameBody,
//                textViewBankTelNumberBody,
//                textViewBankWebsiteBody,
////                textViewCountryBody,
////                textViewCountryBodyLatitude,
////                textViewCountryBodyLongitude
//            ).forEach {
//                it.setOnClickListener(listener)
//            }
//        }
//    }

    private fun registerOnBackPressedCallback() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    binInfoViewModel.onResponseFragmentEvent(
                        BinInfoResponseFragmentEvent.BackButtonPressed
                    )
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.text_view_country_title,
            R.id.text_view_country_body_flag,
            R.id.text_view_country_body,
            R.id.text_view_country_subtitle_latitude,
            R.id.text_view_country_body_latitude,
            R.id.text_view_country_sub_title_longitude,
            R.id.text_view_country_body_longitude,
            -> viewGeoAtMap()

            R.id.text_view_bank_website_body
            -> visitWebsite()

            R.id.text_view_bank_tel_number_body
            -> makeACall()

            else -> {}
        }
    }

    private fun viewGeoAtMap() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            val geo = "${binding?.textViewCountryBodyLatitude?.text.toString()},${binding?.textViewCountryBodyLongitude?.text.toString()}"
            data = Uri.parse("geo:=${geo}")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null)
            context?.startActivity(intent)
    }

    private fun visitWebsite() {
        val webpage: Uri = Uri.parse(" http://${binding?.textViewBankWebsiteBody?.text.toString()}/")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(requireActivity().packageManager) != null)
            context?.startActivity(intent)
    }

    private fun makeACall() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${binding?.textViewBankTelNumberBody?.text.toString()}")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null)
            context?.startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}