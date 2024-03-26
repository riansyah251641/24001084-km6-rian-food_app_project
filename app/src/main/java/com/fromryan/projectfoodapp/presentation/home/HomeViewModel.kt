package com.fromryan.projectfoodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fromryan.projectfoodapp.data.datasource.DataSourceFoodCatalog
import com.fromryan.projectfoodapp.data.datasource.DataSourceFoodCatalogImpl
import com.fromryan.projectfoodapp.data.datasource.DataSourceFoodCategory
import com.fromryan.projectfoodapp.data.datasource.DataSourceFoodCategoryImpl
import com.fromryan.projectfoodapp.data.repository.CatalogRepository
import com.fromryan.projectfoodapp.data.repository.CategoryRepository

class HomeViewModel (
    private val categoryRepository: CategoryRepository,
    private val catalogRepository: CatalogRepository
): ViewModel() {
    private val dataSourceCategory: DataSourceFoodCategory by lazy { DataSourceFoodCategoryImpl() }
    private val dataSourceCatalog: DataSourceFoodCatalog by lazy { DataSourceFoodCatalogImpl()}
    private val _isUsingGridMode = MutableLiveData(false)
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode(){
        val currentvalue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentvalue)
    }

    fun getFoodListData() = categoryRepository.getCategory()
    fun getCatalogData() = catalogRepository.getCatalog()
}