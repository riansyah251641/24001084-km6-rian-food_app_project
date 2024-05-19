package com.fromryan.projectfoodapp.data.mapper

import com.fromryan.projectfoodapp.data.model.Category
import com.fromryan.projectfoodapp.data.source.network.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        image = this?.imgUrl.orEmpty(),
        name = this?.name.orEmpty(),
    )

fun Collection<CategoryItemResponse>?.toCategories() = this?.map { it.toCategory() } ?: listOf()
