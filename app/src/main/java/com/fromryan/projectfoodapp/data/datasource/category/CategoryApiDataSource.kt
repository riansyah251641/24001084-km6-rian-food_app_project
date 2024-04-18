package com.fromryan.projectfoodapp.data.datasource.category

import com.fromryan.projectfoodapp.data.source.network.category.CategoryResponse
import com.fromryan.projectfoodapp.data.source.network.services.ApiDataServices

class CategoryApiDataSource(
    private val service: ApiDataServices
) : CategoryDataSource {
    override suspend fun getCategoryData(): CategoryResponse {
    return service.getCategories()
}
}