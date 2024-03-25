package com.fromryan.projectfoodapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Catalog (
    var category: String,
    var name: String,
    var description: String,
    var price: Double,
    var location: String,
    var image: String,
) : Parcelable