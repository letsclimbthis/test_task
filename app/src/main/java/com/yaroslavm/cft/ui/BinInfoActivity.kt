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
    }
}


/*
TODO:
- on TextView click problem
- start other components using intent
- pass card number to 2nd fragment
- 1st fragment layout
- save history
- rv for history
*/