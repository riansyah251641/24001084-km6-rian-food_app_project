package com.fromryan.projectfoodapp.data.datasource.cart

import app.cash.turbine.test
import com.fromryan.projectfoodapp.data.source.lokal.database.dao.CartDao
import com.fromryan.projectfoodapp.data.source.lokal.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartDatabaseDataSourceTest {
    @MockK
    lateinit var cartDao: CartDao

    private lateinit var cartDataSource: CartDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartDataSource = CartDatabaseDataSource(cartDao)
    }

    @Test
    fun getAllCarts() {
        val entity1 = mockk<CartEntity>()
        val entity2 = mockk<CartEntity>()
        val listEntity = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(listEntity)
            }

        every { cartDao.getAllCarts() } returns mockFlow
        runTest {
            cartDataSource.getAllCarts().test {
                val data = awaitItem()
                assertEquals(listEntity.size, data.size)
                assertEquals(entity1, data[0])
                assertEquals(entity2, data[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun insertCart() {
        runTest {
            val mockEntity = mockk<CartEntity>()
            coEvery { cartDao.insertCart(any()) } returns 1
            val data = cartDataSource.insertCart(mockEntity)
            coVerify { cartDao.insertCart(any()) }
            assertEquals(1, data)
        }
    }

    @Test
    fun updateCart() {
        runTest {
            val mockEntity = mockk<CartEntity>()
            coEvery { cartDao.updateCart(any()) } returns 1
            val data = cartDataSource.updateCart(mockEntity)
            coVerify { cartDao.updateCart(any()) }
            assertEquals(1, data)
        }
    }

    @Test
    fun deleteCart() {
        runTest {
            val mockEntity = mockk<CartEntity>()
            coEvery { cartDao.deleteCart(any()) } returns 1
            val data = cartDataSource.deleteCart(mockEntity)
            coVerify { cartDao.deleteCart(any()) }
            assertEquals(1, data)
        }
    }

    @Test
    fun deleteAll() {
        runTest {
            coEvery { cartDao.deleteAll() } returns Unit
            val data = cartDataSource.deleteAll()
            coVerify { cartDao.deleteAll() }
            assertEquals(Unit, data)
        }
    }
}
