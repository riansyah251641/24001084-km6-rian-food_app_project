package com.fromryan.projectfoodapp.data.datasource.mapper

import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.data.source.lokal.database.entity.CartEntity


fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    catalogId = this?.catalogId.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    catalogName = this?.catalogName.orEmpty(),
    catalogPrice = this?.catalogPrice ?: 0.0,
    catalogImgUrl = this?.catalogImgUrl.orEmpty()
)

fun CartEntity?.toCart() = Cart(
    id = this?.id,
    catalogId = this?.catalogId.orEmpty(),
    itemQuantity = this?.itemQuantity ?: 0,
    catalogName = this?.catalogName.orEmpty(),
    catalogPrice = this?.catalogPrice ?: 0.0,
    catalogImgUrl = this?.catalogImgUrl.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }