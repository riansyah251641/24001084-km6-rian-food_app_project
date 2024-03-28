package com.fromryan.projectfoodapp.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.formatToIDRCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(this)
}