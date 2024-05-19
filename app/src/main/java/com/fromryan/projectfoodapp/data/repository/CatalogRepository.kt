package com.fromryan.projectfoodapp.data.repository

import com.fromryan.projectfoodapp.data.datasource.catalog.CatalogDataSource
import com.fromryan.projectfoodapp.data.mapper.toCatalog_collect
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CatalogRepository {
    fun getCatalog(categoryName: String? = null): Flow<ResultWrapper<List<Catalog>>>
}

class CatalogRepositoryImpl(private val dataSource: CatalogDataSource) : CatalogRepository {
    override fun getCatalog(categoryName: String?): Flow<ResultWrapper<List<Catalog>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val menuData = dataSource.getCatalogData(categoryName).data.toCatalog_collect()
            emit(ResultWrapper.Success(menuData))
        }
    }
}
