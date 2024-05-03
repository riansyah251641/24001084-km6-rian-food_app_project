package com.fromryan.projectfoodapp.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.databinding.FragmentCardBinding
import com.fromryan.projectfoodapp.presentation.checkout.CheckoutActivity
import com.fromryan.projectfoodapp.presentation.common.adapter.CartListAdapter
import com.fromryan.projectfoodapp.presentation.common.adapter.CartListener
import com.fromryan.projectfoodapp.presentation.login.LoginActivity
import com.fromryan.projectfoodapp.utils.formatToIDRCurrency
import com.fromryan.projectfoodapp.utils.hideKeyboard
import com.fromryan.projectfoodapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding

    private val cartViewModel: CardViewModel by viewModel()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(
            object : CartListener {
                override fun onPlusTotalItemCartClicked(cart: Cart) {
                    cartViewModel.increaseCart(cart)
                }

                override fun onMinusTotalItemCartClicked(cart: Cart) {
                    cartViewModel.decreaseCart(cart)
                }

                override fun onRemoveCartClicked(cart: Cart) {
                    cartViewModel.removeCart(cart)
                }

                override fun onUserDoneEditingNotes(cart: Cart) {
                    cartViewModel.setCartNotes(cart)
                    hideKeyboard()
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener {
            checkIfUserLogin()
        }
    }

    private fun setupList() {
        binding.rvCart.itemAnimator = null
        binding.rvCart.adapter = adapter
    }

    private fun observeData() {
        cartViewModel.getAllCarts().observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = false
                binding.layoutState.ivError.isVisible = false
                binding.clInformationOrder.isVisible = true
                binding.rvCart.isVisible = true
                result.payload?.let { (cart, totalPrice) ->
                    adapter.submitData(cart)
                    binding.tvTotalPrice.text = totalPrice.formatToIDRCurrency()
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = true
                binding.layoutState.ivError.isVisible = false
                binding.clInformationOrder.isVisible = false
                binding.rvCart.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = false
                binding.layoutState.ivError.isVisible = true
                binding.layoutState.ivError.setImageResource(R.drawable.iv_empty_display)
                binding.clInformationOrder.isVisible = false
                binding.rvCart.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = false
                binding.layoutState.ivError.isVisible = true
                binding.clInformationOrder.isVisible = false
                binding.layoutState.ivError.setImageResource(R.drawable.iv_empty_display)
                data.payload?.let { (_, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.formatToIDRCurrency()
                }
                binding.rvCart.isVisible = false
            })
        }
    }

    private fun checkIfUserLogin() {
        if (cartViewModel.isUserLoggedIn()) {
            context?.startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        } else {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}
