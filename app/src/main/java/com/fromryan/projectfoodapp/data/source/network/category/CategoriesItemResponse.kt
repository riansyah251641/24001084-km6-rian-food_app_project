package com.fromryan.projectfoodapp.data.source.network.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("nama")
    val name: String?,
)
