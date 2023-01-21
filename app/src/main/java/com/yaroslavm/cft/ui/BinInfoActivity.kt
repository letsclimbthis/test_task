package com.yaroslavm.cft.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yaroslavm.cft.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BinInfoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bin_info_activity)
//        val binding = BinInfoActivityBinding.inflate(layoutInflater)
//        setSupportActionBar(findViewById(R.id.toolbar))
    }
}


/*
TODO:
- save history
- response with code 40* exception
*/