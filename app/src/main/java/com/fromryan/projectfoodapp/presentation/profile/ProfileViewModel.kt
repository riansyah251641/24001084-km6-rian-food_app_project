package com.fromryan.projectfoodapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fromryan.projectfoodapp.data.model.Profile
import com.fromryan.projectfoodapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {


    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun getCurrentUser() = repo.getCurrentUser()


    fun isUserLoggedIn() = repo.isLoggedIn()

    fun doLogout() {
        repo.doLogout()
    }
}