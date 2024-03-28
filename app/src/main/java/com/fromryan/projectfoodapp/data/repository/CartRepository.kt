package com.fromryan.projectfoodapp.data.repository

import com.fromryan.projectfoodapp.data.datasource.cart.CartDataSource
import com.fromryan.projectfoodapp.data.datasource.mapper.toCartEntity
import com.fromryan.projectfoodapp.data.datasource.mapper.toCartList
import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.source.lokal.database.entity.CartEntity
import com.fromryan.projectfoodapp.utils.ResultWrapper
import com.fromryan.projectfoodapp.utils.proceed
import com.fromryan.projectfoodapp.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException


interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>>
    fun createCart(
        catalog: Catalog,
        quantity: Int,
        notes: String? = null
    ): Flow<ResultWrapper<Boolean>>

    fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(private val cartDataSource: CartDataSource) : CartRepository {

    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>> {
        return cartDataSource.getAllCarts()
            .map {
                //mapping into cart list and sum the total price
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf { it.catalogPrice * it.itemQuantity }
                    Pair(result, totalPrice)
                }
            }.map {
                //map to check when list is empty
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun createCart(
        catalog: Catalog,
        quantity: Int,
        notes: String?
    ): Flow<ResultWrapper<Boolean>> {
        return catalog.id?.let { catalogId ->
            //when id is not null
            proceedFlow {
                val affectedRow = cartDataSource.insertCart(
                    CartEntity(
                        catalogId = catalogId,
                        itemQuantity = quantity,
                        catalogName = catalog.name,
                        catalogImgUrl = catalog.image,
                        catalogPrice = catalog.price,
                        itemNotes = notes
                    )
                )
                delay(2000)
                affectedRow > 0
            }
        } ?: flow {
            //when id is doesnt exist
            emit(ResultWrapper.Error(IllegalStateException("catalog ID not found")))
        }
    }

    override fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }
}