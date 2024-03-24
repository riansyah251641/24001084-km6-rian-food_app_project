package com.fromryan.projectfoodapp.presentation.detailfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.fromryan.projectfoodapp.databinding.ActivityCheckoutBinding

class DetailFoodActivity : AppCompatActivity() {

    private val binding : ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}