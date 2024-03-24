package com.fromryan.projectfoodapp.data.model

import androidx.annotation.DrawableRes

data class Catalog (
    var category: String,
    var name: String,
    var description: String,
    var price: Double,
    var location: String,
    var image: String,
)