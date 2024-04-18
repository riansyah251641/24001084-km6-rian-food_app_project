package com.fromryan.projectfoodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.CatalogRepository
import com.fromryan.projectfoodapp.data.repository.CategoryRepository
import com.fromryan.projectfoodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class HomeViewModel (
    private val categoryRepository: CategoryRepository,
    private val catalogRepository: CatalogRepository,
    private val cartRepository: CartRepository,
): ViewModel() {
    private val addCatalog = MutableLiveData(0).apply {
        postValue(0)
    }

    fun getCategoryData() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
    fun getCatalogData(categoryName: String? = null) =
        catalogRepository.getCatalog(categoryName).asLiveData(Dispatchers.IO)


    fun addItemToCart(menu: Catalog) {
        addCatalog.value = 1

        cartRepository.createCart(menu, 1)
            .asLiveData(Dispatchers.IO)
            .observeForever { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Produk berhasil ditambahkan")
                    }

                    is ResultWrapper.Error -> {
                        println("Error")
                    }

                    is ResultWrapper.Loading -> {
                        println("Wait...")
                    }

                    else -> {
                    }
                }
            }
    }

}