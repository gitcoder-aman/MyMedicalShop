package com.tech.mymedicalshopuser.utils

import kotlin.math.roundToInt

fun totalPriceCalculate(
     subTotalPrice: Float
) : Int{
    val deliveryCharge = calculateDeliveryCharge(subTotalPrice)
    val taxCharge = calculateTaxCharge(subTotalPrice)
    val discount = calculateDiscount(subTotalPrice)
    val totalPrice = (subTotalPrice+deliveryCharge+taxCharge-discount).roundToInt()

    return totalPrice
}
fun calculateTaxCharge(
    subTotalPrice: Float
) : Float{
    val taxCharge = (subTotalPrice*18/100)
    return taxCharge
}
fun calculateDiscount(
    subTotalPrice: Float
) : Float{
    val discount = (subTotalPrice*18/100)
    return discount
}
fun calculateDeliveryCharge(
    subTotalPrice: Float
) : Float{
    val deliveryCharge = if(subTotalPrice > 1000) 0f else 20f
    return deliveryCharge
}