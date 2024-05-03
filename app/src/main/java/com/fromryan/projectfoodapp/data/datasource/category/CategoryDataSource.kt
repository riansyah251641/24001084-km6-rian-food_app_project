package com.fromryan.projectfoodapp.data.datasource.category

import com.fromryan.projectfoodapp.data.source.network.category.CategoryResponse

interface CategoryDataSource {
    suspend fun getCategoryData(): CategoryResponse
}
