package com.tech.mymedicalshopuser.data.response.order

import kotlinx.serialization.Serializable

@Serializable
data class MedicalOrderResponseItem(
    val delivery_charge: Int,
    val id: Int = 0,
    val isApproved: Int,
    val order_date: String,
    val order_id: String = "",
    val product_category: String,
    val product_image_id: String,
    val product_id: String,
    val product_name: String,
    val product_price: Int,
    val product_quantity: Int,
    val subtotal_price: Int,
    val tax_charge: Int,
    val totalPrice: Int,
    val user_id: String,
    val user_name: String,
    val user_address: String,
    val user_pinCode: String,
    val user_mobile: String,
    val user_email: String,
    val order_status: String,
    val order_cancel_status: String,
    val user_street: String,
    val user_city: String,
    val user_state: String,
    val discount_price: String,
    val shipped_date: String,
    val out_of_delivery_date: String,
    val delivered_date: String,
)