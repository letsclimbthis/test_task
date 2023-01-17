package com.yaroslavm.cft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BinInfoActivity: AppCompatActivity() {

//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bin_info_activity)
    }


//    override fun onSupportNavigateUp(): Boolean {
////        return navController.navigateUp() || super.onSupportNavigateUp()
////        val navController = findNavController(R.id.nav_host_fragment)
//        val navController = (supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
//            .navController
//        return navController.navigateUp()
//    }
}