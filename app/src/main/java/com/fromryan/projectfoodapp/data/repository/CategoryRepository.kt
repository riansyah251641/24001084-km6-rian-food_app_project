package com.fromryan.projectfoodapp.data.repository

import com.fromryan.projectfoodapp.data.datasource.category.DataSourceFoodCategory
import com.fromryan.projectfoodapp.data.model.Category

interface CategoryRepository {
    fun getCategory(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: DataSourceFoodCategory): CategoryRepository{
    override fun getCategory(): List<Category> = dataSource.getFoodCategoryItem()
}