package com.fromryan.projectfoodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fromryan.projectfoodapp.data.repository.CatalogRepository
import com.fromryan.projectfoodapp.data.repository.CategoryRepository

class HomeViewModel (
    private val categoryRepository: CategoryRepository,
    private val catalogRepository: CatalogRepository
): ViewModel() {
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