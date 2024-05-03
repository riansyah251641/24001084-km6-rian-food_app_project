package com.fromryan.projectfoodapp.data.datasource.catalog

import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogResponse
import com.fromryan.projectfoodapp.data.source.network.services.ApiDataServices

class CatalogApiDataSource(
    private val service: ApiDataServices,
) : CatalogDataSource {
    override suspend fun getCatalogData(categoryName: String?): CatalogResponse {
        return service.getMenu(categoryName)
    }
}
