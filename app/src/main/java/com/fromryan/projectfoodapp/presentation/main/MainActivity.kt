package com.fromryan.projectfoodapp.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val themeTitleList = arrayOf("Light", "Dark", "Auto (System Default)")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        setupBootomNav()
        setSwitchMode()
    }

    private fun setupBootomNav(){
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

    private fun setSwitchMode(){
//        var switchMode : SwitchCompat
//        var nightMode: Boolean
//        var sharedPreferences: SharedPreferences
//        var editor : SharedPreferences.Editor
//
//        switchMode = findViewById(R.id.switchMode)
//        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
//        nightMode = sharedPreferences.getBoolean("nightMode",false)
//
//        if (nightMode){
//            switchMode.isChecked = true
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }
//        switchMode.setOnClickListener {
//                if(nightMode){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    editor = sharedPreferences.edit()
//                    editor.putBoolean("nightMode", false)
//                }else{
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    editor = sharedPreferences.edit()
//                    editor.putBoolean("nightMode", true)
//                }
//                editor.apply()
//            }
        val sharedPreferencesManager = SharedPreferenceMainManager(this)
        var checkedTheme = sharedPreferencesManager.theme
        val  themeDialog = MaterialAlertDialogBuilder(this)
            .setTitle("Theme")
            .setPositiveButton("ok"){_,_ ->
                sharedPreferencesManager.theme = checkedTheme
                AppCompatDelegate.setDefaultNightMode(sharedPreferencesManager.themeFlag[checkedTheme])
            }
            .setSingleChoiceItems(themeTitleList,checkedTheme){_, which ->
                checkedTheme = which
            }
            .setCancelable(false)
            binding.switchMode.setOnClickListener {
                themeDialog.show()
        }
    }
}