package com.fromryan.projectfoodapp.data.repository

import app.cash.turbine.test
import com.fromryan.projectfoodapp.data.datasource.catalog.CatalogDataSource
import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogItemResponse
import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogResponse
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

class CatalogRepositoryImplTest {
    @MockK
    lateinit var datasource: CatalogDataSource

    @MockK
    lateinit var userRepository: UserRepository
    private lateinit var catalogRepository: CatalogRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        catalogRepository = CatalogRepositoryImpl(datasource)
    }

    @Test
    fun getCatalog() {
        val Catalog1 =
            CatalogItemResponse(
                imgUrl = "ImgUrl1",
                name = "name1",
                formattedPrice = "Rp price1",
                price = 8000.0,
                menuDesc = "deskripsi1",
                restoAddress = "address1",
            )
        val Catalog2 =
            CatalogItemResponse(
                imgUrl = "ImgUrl2",
                name = "name2",
                formattedPrice = "Rp price2",
                price = 16000.0,
                menuDesc = "deskripsi2",
                restoAddress = "address2",
            )

        val mockListCatalog = listOf(Catalog1, Catalog2)
        val mockResponse = mockk<CatalogResponse>()
        every { mockResponse.data } returns mockListCatalog
        runTest {
            coEvery { datasource.getCatalogData(any()) } returns mockResponse
            catalogRepository.getCatalog().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getCatalogData(any()) }
            }
        }
    }

    @Test
    fun getCatalogNull() {
        val Catalog1 =
            CatalogItemResponse(
                imgUrl = "ImgUrl1",
                name = "name1",
                formattedPrice = "Rp price1",
                price = 8000.0,
                menuDesc = "deskripsi1",
                restoAddress = "address1",
            )
        val Catalog2 =
            CatalogItemResponse(
                imgUrl = "ImgUrl2",
                name = "name2",
                formattedPrice = "Rp price2",
                price = 16000.0,
                menuDesc = "deskripsi2",
                restoAddress = "address2",
            )

        val mockListCatalog = listOf(Catalog1, Catalog2)
        val mockResponse = mockk<CatalogResponse>()
        every { mockResponse.data } returns mockListCatalog
        runTest {
            coEvery { datasource.getCatalogData(any()) } returns mockResponse
            catalogRepository.getCatalog().map {
                delay(100)
                it
            }.test {
                delay(2201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getCatalogData(any()) }
            }
        }
    }

    @Test
    fun createOrder() {
    }
}
