package com.fromryan.projectfoodapp.data.datasource.catalog

import com.fromryan.projectfoodapp.data.datasource.category.CategoryApiDataSource
import com.fromryan.projectfoodapp.data.datasource.category.CategoryDataSource
import com.fromryan.projectfoodapp.data.source.network.category.CategoryResponse
import com.fromryan.projectfoodapp.data.source.network.services.ApiDataServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryApiDataSourceTest {
    @MockK
    lateinit var service: ApiDataServices

    private lateinit var dataSource: CategoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CategoryApiDataSource(service)
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoryResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val actualResult = dataSource.getCategoryData()
            coVerify { service.getCategories() }
            assertEquals(mockResponse, actualResult)
        }
    }
}
