package com.fromryan.projectfoodapp.data.datasource

import com.fromryan.projectfoodapp.data.model.Catalog

interface DataSourceFoodCatalog{
    fun getFoodCatalogItem(): List<Catalog>
}
