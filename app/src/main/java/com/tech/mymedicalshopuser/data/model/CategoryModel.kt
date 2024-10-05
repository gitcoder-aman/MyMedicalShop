package com.tech.mymedicalshopuser.data.model

import com.tech.mymedicalshopuser.R

data class CategoryModel(
    val itemName : String,
    val itemImage : Int
)

val categoryList = listOf(
    CategoryModel(
        itemName = "Capsules",
        itemImage = R.drawable.capsules
    ),
    CategoryModel(
        itemName = "Health",
        itemImage = R.drawable.healthcare
    ),
    CategoryModel(
        itemName = "X-ray",
        itemImage = R.drawable.x_ray
    ),
    CategoryModel(
        itemName = "Syringe",
        itemImage = R.drawable.syringe
    ),
    CategoryModel(
        itemName = "Capsules",
        itemImage = R.drawable.capsules
    ),
    CategoryModel(
        itemName = "Health",
        itemImage = R.drawable.healthcare
    ),
    CategoryModel(
        itemName = "X-ray",
        itemImage = R.drawable.x_ray
    ),
    CategoryModel(
        itemName = "Syringe",
        itemImage = R.drawable.syringe
    ),
)