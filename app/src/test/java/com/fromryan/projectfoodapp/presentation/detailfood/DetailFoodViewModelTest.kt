package com.fromryan.projectfoodapp.presentation.detailfood

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
import com.catnip.kokomputer.tools.getOrAwaitValue
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailFoodViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var intent: Bundle

    private lateinit var viewModel: DetailFoodViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val expectedCatalog =
            Catalog(
                "1",
                "name1",
                "desk1",
                5000.0,
                "location1",
                "iamge1",
            )
        every { intent.getParcelable<Catalog>(DetailFoodActivity.EXTRAS_ITEM) } returns expectedCatalog
        every { intent.getParcelable<Catalog>(DetailFoodActivity.EXTRA_CATALOG) } returns expectedCatalog
        viewModel =
            spyk(
                DetailFoodViewModel(
                    intent, cartRepository,
                ),
            )
    }

    @Test
    fun getProduct() {
    }

    @Test
    fun getProductCountLiveData() {
        val menuCount = viewModel.productCountLiveData.getOrAwaitValue()
        assertEquals(0, menuCount)
    }

    @Test
    fun getPriceLiveData() {
        val price = viewModel.priceLiveData.getOrAwaitValue()
        assertEquals(0.0, price, 0.0)
    }

    @Test
    fun add() {
        viewModel.add()
        assertEquals(1, viewModel.productCountLiveData.getOrAwaitValue())
        assertEquals(5000.0, viewModel.priceLiveData.getOrAwaitValue(), 0.0)
    }

    @Test
    fun minus() {
        viewModel.add()
        viewModel.minus()
        assertEquals(0, viewModel.productCountLiveData.getOrAwaitValue())
        assertEquals(0.0, viewModel.priceLiveData.getOrAwaitValue(), 0.0)
    }

    @Test
    fun addToCart() {
        runTest {
            val catalog =
                Catalog(
                    "1",
                    "name1",
                    "desk1",
                    5000.0,
                    "location1",
                    "iamge1",
                )

            coEvery { cartRepository.createCart(catalog, any()) } returns
                flow {
                    emit(ResultWrapper.Success(true))
                }

            viewModel.add()
            val result = viewModel.addToCart().getOrAwaitValue()

            assertTrue(result is ResultWrapper.Success)
            assertEquals(true, (result as ResultWrapper.Success).payload)
        }
    }
}
