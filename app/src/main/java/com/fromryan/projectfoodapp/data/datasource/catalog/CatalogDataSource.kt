package com.fromryan.projectfoodapp.data.datasource.catalog

import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogResponse

interface CatalogDataSource {
    suspend fun getCatalogData(categoryName: String? = null): CatalogResponse
}
