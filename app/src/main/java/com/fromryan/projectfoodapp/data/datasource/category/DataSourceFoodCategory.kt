package com.fromryan.projectfoodapp.data.datasource.category

import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Category

interface DataSourceFoodCategory {
    fun getFoodCategoryItem(): List<Category>
}
