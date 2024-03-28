package com.fromryan.projectfoodapp.presentation.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.core.view.isVisible
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.datasource.cart.CartDataSource
import com.fromryan.projectfoodapp.data.datasource.cart.CartDatabaseDataSource
import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.CartRepositoryImpl
import com.fromryan.projectfoodapp.data.source.lokal.database.AppDatabase
import com.fromryan.projectfoodapp.databinding.FragmentCardBinding
import com.fromryan.projectfoodapp.presentation.checkout.CheckoutActivity
import com.fromryan.projectfoodapp.presentation.common.adapter.CartListAdapter
import com.fromryan.projectfoodapp.presentation.common.adapter.CartListener
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory
import com.fromryan.projectfoodapp.utils.formatToIDRCurrency
import com.fromryan.projectfoodapp.utils.hideKeyboard
import com.fromryan.projectfoodapp.utils.proceedWhen


class CardFragment : Fragment() {

    private lateinit var binding: FragmentCardBinding

    private val viewModel: CardViewModel by viewModels {
        val database = AppDatabase.getInstance(requireContext())
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CardViewModel(repo))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener {
            context?.startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun setupList() {
        binding.rvCart.itemAnimator = null
        binding.rvCart.adapter = adapter
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.ivError.isVisible = false
                binding.rvCart.isVisible = true
                result.payload?.let { (cart, totalPrice) ->
                    adapter.submitData(cart)
                    binding.tvTotalPrice.text = totalPrice.formatToIDRCurrency()
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.ivError.isVisible = false
                binding.rvCart.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.ivError.isVisible = true
                binding.layoutState.ivError.setImageResource(R.drawable.iv_empty_display)
                binding.rvCart.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.ivError.isVisible = true
                binding.layoutState.ivError.setImageResource(R.drawable.iv_empty_display)
                data.payload?.let { (_, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.formatToIDRCurrency()
                }
                binding.rvCart.isVisible = false
            })
        }
    }

}