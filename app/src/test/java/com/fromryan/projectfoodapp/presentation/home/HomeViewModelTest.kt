package com.fromryan.projectfoodapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
import com.catnip.kokomputer.tools.getOrAwaitValue
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.CatalogRepository
import com.fromryan.projectfoodapp.data.repository.CategoryRepository
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var catalogRepository: CatalogRepository

    @MockK
    lateinit var cartRepository: CartRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository,
                    catalogRepository,
                    cartRepository,
                ),
            )
    }

    @Test
    fun getCatalogData() {
        every { catalogRepository.getCatalog(any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }

        val result = viewModel.getCatalogData().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { catalogRepository.getCatalog(any()) }
    }

    @Test
    fun getCategoryData() {
        every { categoryRepository.getCategories() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }

        val result = viewModel.getCategoryData().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepository.getCategories() }
    }


}
