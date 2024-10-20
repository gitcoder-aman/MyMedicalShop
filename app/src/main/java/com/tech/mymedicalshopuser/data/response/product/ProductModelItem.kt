package com.tech.mymedicalshopuser.data.response.product

data class ProductModelItem(
    val id: Int,
    val product_category: String,
    val product_description: String,
    val product_expiry_date: String,
    val product_id: String,
    val product_image_id: String,
    val product_name: String,
    val product_power: String,
    val product_price: Int,
    val product_rating: Double,
    val product_stock: Int
)