package com.fromryan.projectfoodapp.data.source.network.catalog

import com.google.gson.annotations.SerializedName

data class CatalogResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<CatalogItemResponse>,
)
