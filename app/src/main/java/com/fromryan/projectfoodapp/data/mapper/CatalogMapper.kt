package com.fromryan.projectfoodapp.data.mapper

import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.source.network.catalog.CatalogItemResponse

fun CatalogItemResponse?.toCatalog() =
    Catalog(
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        image = this?.imgUrl.orEmpty(),
        description = this?.menuDesc.orEmpty(),
        location = this?.restoAddress.orEmpty(),
    )

fun Collection<CatalogItemResponse>?.toCatalog_collect() =
    this?.map { it.toCatalog() } ?: listOf()