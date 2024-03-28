package com.fromryan.projectfoodapp.data.source.lokal.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "catalog_id")
    var catalogId: String? = null,
    @ColumnInfo(name = "catalog_name")
    var catalogName: String,
    @ColumnInfo(name = "catalog_img_url")
    var catalogImgUrl: String,
    @ColumnInfo(name = "catalog_price")
    var catalogPrice: Double,
    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int = 0,
    @ColumnInfo(name = "item_notes")
    var itemNotes: String? = null,
)