package com.fromryan.projectfoodapp.data.model

data class Cart(
    var id: Int? = null,
    var catalogId: String? = null,
    var catalogName: String,
    var catalogImgUrl: String,
    var catalogPrice: Double,
    var itemQuantity: Int = 1,
    var itemNotes: String? = null
)