package com.fromryan.projectfoodapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fromryan.projectfoodapp.data.model.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            name = "Muhammad Febriansyah",
            email = "work.rsyah1641@gmail.com",
            profileImg = "https://i.pinimg.com/564x/19/99/e0/1999e052d1f7263e4674294285f7c576.jpg",
            nohp = "082144445148",
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}