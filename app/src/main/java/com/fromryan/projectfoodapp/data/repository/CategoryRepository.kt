package com.fromryan.projectfoodapp.data.repository

import com.fromryan.projectfoodapp.data.datasource.category.CategoryDataSource
import com.fromryan.projectfoodapp.data.model.Category
import com.fromryan.projectfoodapp.utils.ResultWrapper
import com.fromryan.projectfoodapp.data.mapper.toCategories
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CategoryRepository {
    fun getCategories() : Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource): CategoryRepository{
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return flow {
            emit(ResultWrapper.Loading())
            delay(1000)
            val categoryData = dataSource.getCategoryData().data.toCategories()
            emit(ResultWrapper.Success(categoryData))
        }
    }

}