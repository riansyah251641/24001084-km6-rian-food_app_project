package com.fromryan.projectfoodapp.data.repository

import app.cash.turbine.test
import com.fromryan.projectfoodapp.data.datasource.category.CategoryDataSource
import com.fromryan.projectfoodapp.data.source.network.category.CategoryItemResponse
import com.fromryan.projectfoodapp.data.source.network.category.CategoryResponse
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var dataSource: CategoryDataSource
    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CategoryRepositoryImpl(dataSource)
    }

    @Test
    fun getCategorySuccess() {
        val category1 =
            CategoryItemResponse(
                imgUrl = "imageUrl1",
                name = "name1",
            )
        val category2 =
            CategoryItemResponse(
                imgUrl = "imageUrl2",
                name = "name2",
            )
        val mockListCategory = listOf(category1, category2)
        val mockResponse = mockk<CategoryResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryData() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun getCategoryLoading() {
    }

    @Test
    fun getCategoryError() {
    }

    @Test
    fun getCategoryEmpty() {
    }
}
