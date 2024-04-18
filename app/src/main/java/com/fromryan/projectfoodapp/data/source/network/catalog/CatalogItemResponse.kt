package com.fromryan.projectfoodapp.data.source.network.catalog

import com.google.gson.annotations.SerializedName

data class CatalogItemResponse (
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("harga")
    val price: Double?,
    @SerializedName("detail")
    val menuDesc: String?,
    @SerializedName("alamat_resto")
    val restoAddress: String?,
)