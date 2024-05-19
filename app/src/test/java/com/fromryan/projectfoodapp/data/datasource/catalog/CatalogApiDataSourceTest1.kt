package com.fromryan.projectfoodapp.data.datasource.catalog

import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogResponse
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

class CatalogApiDataSourceTest {
    @MockK
    lateinit var service: ApiDataServices
    private lateinit var dataSource: CatalogDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CatalogApiDataSource(service)
    }

    @Test
    fun getCatalogData() {
        runTest {
            val mockResponse = mockk<CatalogResponse>(relaxed = true)
            coEvery { service.getCatalog(any()) } returns mockResponse
            val actualResult = dataSource.getCatalogData()
            coVerify { service.getCatalog(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }
}
