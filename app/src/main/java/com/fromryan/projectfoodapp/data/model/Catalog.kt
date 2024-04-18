package com.fromryan.projectfoodapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Catalog (
    var id: String? = UUID.randomUUID().toString(),
    var name: String,
    var description: String,
    var price: Double,
    var location: String,
    var image: String,
) : Parcelable