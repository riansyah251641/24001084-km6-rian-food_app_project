package com.fromryan.projectfoodapp.data.datasource

import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Category

interface DataSourceFoodCategory {
    fun getFoodCategoryItem(): List<Category>
}
