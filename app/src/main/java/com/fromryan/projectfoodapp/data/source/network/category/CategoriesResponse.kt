package com.fromryan.projectfoodapp.data.source.network.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<CategoryItemResponse>,
)
