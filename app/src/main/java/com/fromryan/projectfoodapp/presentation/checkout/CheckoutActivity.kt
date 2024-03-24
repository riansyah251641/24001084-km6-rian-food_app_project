package com.fromryan.projectfoodapp.presentation.checkout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private val binding : ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}