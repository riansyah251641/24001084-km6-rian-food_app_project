package com.fromryan.projectfoodapp.data.datasource

import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Category

interface DataSourceFoodCategory {
    fun getFoodCategoryItem(): List<Category>
}

class DataSourceFoodCategoryImpl(): DataSourceFoodCategory {
    override fun getFoodCategoryItem(): List<Category> {
        return mutableListOf(
            Category(
                name = "Pizza",
                image = R.drawable.category_pizza,
            ),
            Category(
                image = R.drawable.category_hamburger,
                name = "Hamburger"
            ),
            Category(
                image = R.drawable.category_hotdog,
                name = "Hot Dog"
            ),
            Category(
                image = R.drawable.category_ramen,
                name = "Ramen"
            ),
            Category(
                image = R.drawable.category_salad,
                name = "Salad"
            ),
            Category(
                image = R.drawable.category_pizza,
                name = "isi lain 1"
            ),
            Category(
                image = R.drawable.category_hamburger,
                name = "isi lain 2"
            ),
            Category(
                image = R.drawable.category_hotdog,
                name = "isi lain 3"
            ),
        )
    }
}