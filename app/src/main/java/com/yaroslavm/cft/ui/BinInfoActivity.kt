package com.yaroslavm.cft.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.yaroslavm.cft.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BinInfoActivity: AppCompatActivity() {

    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bin_info_activity)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}