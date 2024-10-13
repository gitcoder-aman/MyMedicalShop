package com.tech.mymedicalshopuser.ui_layer.navigation

import kotlinx.serialization.Serializable

@Serializable
object SignInRoute


@Serializable
object SignUpRoute

@Serializable
object StartScreenRoute

@Serializable
object HomeScreenRoute

@Serializable
object CartScreenRoute

@Serializable
object OrderScreenRoute

@Serializable
object ProfileScreenRoute

@Serializable
data class ProductDetailScreenRoute(
    val productName: String,
    val productId: String,
    val productImage: String,
    val productPrice: Int,
    val productRating: Float,
    val productStock: Int,
    val productDescription: String,
    val productPower: String,
    val productCategory: String,
    val productExpiryDate: String,

    )

@Serializable
data class VerificationScreenRoute(
    val userId : String
)