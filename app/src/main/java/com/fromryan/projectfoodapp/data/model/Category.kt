package com.fromryan.projectfoodapp.data.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class Category (
    var id:String = UUID.randomUUID().toString(),
    var image : String,
    var name :String
)