package com.fromryan.projectfoodapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        setupBootomNav()
    }

    private fun setupBootomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

    fun navigateToProfile() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.selectedItemId = R.id.navigation_profile
        navController.navigate(R.id.navigation_profile)
    }
}
