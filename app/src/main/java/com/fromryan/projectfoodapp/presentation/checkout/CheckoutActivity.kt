package com.fromryan.projectfoodapp.presentation.checkout

import PriceListAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.datasource.cart.CartDataSource
import com.fromryan.projectfoodapp.data.datasource.cart.CartDatabaseDataSource
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.CartRepositoryImpl
import com.fromryan.projectfoodapp.data.source.lokal.database.AppDatabase
import com.fromryan.projectfoodapp.databinding.ActivityCheckoutBinding
import com.fromryan.projectfoodapp.presentation.common.adapter.CartListAdapter
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory
import com.fromryan.projectfoodapp.utils.formatToIDRCurrency
import com.fromryan.projectfoodapp.utils.proceedWhen


class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

    private val binding : ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }
    private fun observeData() {
        viewModel.checkoutData.observe(this) {result ->
            result.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.ivError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvSectionOrder.isVisible = true
                result.payload?.let { (carts,priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalPrice.text = totalPrice.formatToIDRCurrency()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.ivError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.ivError.isVisible = true
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.ivError.isVisible = true
                binding.layoutState.ivError.setImageResource(R.drawable.iv_empty_display)
                data.payload?.let { (_, totalPrice) ->
                    binding.tvTotalPrice.text = "Rp 0"
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            })
        }
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnCheckout.setOnClickListener{
            viewModel.deleteAllCarts()
            checkoutDialog()
        }
    }

    private fun checkoutDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.layout_dialog_checkout)

        val btnClose = dialog.findViewById<Button>(R.id.iv_btn_close)
        btnClose.setOnClickListener{
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }


}