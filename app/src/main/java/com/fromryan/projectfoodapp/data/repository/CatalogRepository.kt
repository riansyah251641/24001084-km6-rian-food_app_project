package com.fromryan.projectfoodapp.data.repository

import com.fromryan.projectfoodapp.data.datasource.DataSourceFoodCatalog
import com.fromryan.projectfoodapp.data.model.Catalog

interface CatalogRepository {
    fun getCatalog(): List<Catalog>
}

class CatalogRepositoryImpl(private val dataSource : DataSourceFoodCatalog) :CatalogRepository{
    override fun getCatalog(): List<Catalog> = dataSource.getFoodCatalogItem()
}