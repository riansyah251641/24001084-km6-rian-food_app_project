package com.fromryan.projectfoodapp.data.repository

import app.cash.turbine.test
import com.fromryan.projectfoodapp.data.datasource.cart.CartDataSource
import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.model.PriceItem
import com.fromryan.projectfoodapp.data.source.lokal.database.entity.CartEntity
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var dataSource: CartDataSource
    private lateinit var repository: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(dataSource)
    }

    @Test
    fun cartSuccessCreated() {
        val entity1 =
            CartEntity(
                id = 1,
                catalogId = "id1",
                catalogName = "name1",
                catalogImgUrl = "imageUrl1",
                catalogPrice = 8000.0,
                itemQuantity = 2,
                itemNotes = "notes1",
            )
        val entity2 =
            CartEntity(
                id = 2,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 1,
                itemNotes = "notes2",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(mockList.size, data.payload?.first?.size)
                assertEquals(61000.0, data.payload?.second)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun getCartInLoading() {
        val entity1 =
            CartEntity(
                id = 1,
                catalogId = "id1",
                catalogName = "name1",
                catalogImgUrl = "imageUrl1",
                catalogPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "notes1",
            )
        val entity2 =
            CartEntity(
                id = 2,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 5,
                itemNotes = "notes2",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun getCartInEmpty() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutSuccess() {
        val entity1 =
            CartEntity(
                id = 1,
                catalogId = "id1",
                catalogName = "name1",
                catalogImgUrl = "imageUrl1",
                catalogPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "notes1",
            )
        val entity2 =
            CartEntity(
                id = 2,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 5,
                itemNotes = "notes2",
            )
        val listPriceitems =
            PriceItem(
                name = "name1",
                total = 24000.0,
            )
        val priceItem2 =
            PriceItem(
                name = "name2",
                total = 225000.0,
            )
        val mockList = listOf(entity1, entity2)
        val mockPrice = listOf(listPriceitems, priceItem2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(mockList.size, data.payload?.first?.size)
                assertEquals(mockPrice.size, data.payload?.second?.size)
                assertEquals(249000.0, data.payload?.third)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutLoading() {
        val entity1 =
            CartEntity(
                id = 1,
                catalogId = "id1",
                catalogName = "name1",
                catalogImgUrl = "imageUrl1",
                catalogPrice = 8000.0,
                itemQuantity = 3,
                itemNotes = "notes1",
            )
        val entity2 =
            CartEntity(
                id = 2,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 5,
                itemNotes = "notes2",
            )

        val mockList = listOf(entity1, entity2)
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutError() {
    }

    @Test
    fun getCheckoutEmpty() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repository.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun createCartError() {
        val mockProduct = mockk<Catalog>(relaxed = true)
        every { mockProduct.id } returns null
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("Error")
        runTest {
            repository.createCart(mockProduct, 1, "nama1")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(210)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Error)
                    coVerify(atLeast = 0) { dataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun decreaseItemCart() {
        val mockCart =
            Cart(
                id = 1,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 5,
                itemNotes = "notes2",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 0) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseItemCart() {
        val mockCart =
            Cart(
                id = 1,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 5,
                itemNotes = "notes2",
            )
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        val mockCart =
            Cart(
                id = 1,
                catalogId = "id2",
                catalogName = "name2",
                catalogImgUrl = "imageUrl2",
                catalogPrice = 45000.0,
                itemQuantity = 5,
                itemNotes = "notes2",
            )
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repository.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
    }

    @Test
    fun deleteAll() {
    }
}
