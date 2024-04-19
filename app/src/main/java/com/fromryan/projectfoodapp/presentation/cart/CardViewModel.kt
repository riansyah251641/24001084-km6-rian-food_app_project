package com.fromryan.projectfoodapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class CardViewModel(
    private val repo: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    fun getAllCarts() = repo.getUserCartData().asLiveData(Dispatchers.IO)

    fun decreaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.decreaseCart(item).collect() }
    }

    fun increaseCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.increaseCart(item).collect() }
    }

    fun removeCart(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteCart(item).collect() }
    }

    fun setCartNotes(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) { repo.setCartNotes(item).collect() }
    }

    fun isUserLoggedIn() = userRepository.isLoggedIn()
}