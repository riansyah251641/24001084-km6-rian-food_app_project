package com.fromryan.projectfoodapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fromryan.projectfoodapp.data.model.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            name = "Muhammad Febriansyah",
            email = "work.rsyah1641@gmail.com",
            profileImg = "https://avatars.githubusercontent.com/u/21256595?v=4",
            nohp = "hermasyp",
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}