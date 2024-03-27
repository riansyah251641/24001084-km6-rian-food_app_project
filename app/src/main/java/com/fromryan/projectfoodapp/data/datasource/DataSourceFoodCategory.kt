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
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_pizza.png?raw=true",
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_hamburger.png?raw=true",
                name = "Hamburger"
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_hotdog.png?raw=true",
                name = "Hot Dog"
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_ramen.png?raw=true",
                name = "Ramen"
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_salad.png?raw=true",
                name = "Salad"
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_pizza.png?raw=true",
                name = "isi lain 1"
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_hamburger.png?raw=true",
                name = "isi lain 2"
            ),
            Category(
                image = "https://github.com/riansyah251641/food_app_asset/blob/main/category/category_hotdog.png?raw=true",
                name = "isi lain 3"
            ),
        )
    }
}