package com.tech.mymedicalshopuser.ui_layer.screens.cart_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.bottomNavigation.NavigationView
import com.tech.mymedicalshopuser.ui_layer.component.CartItem
import com.tech.mymedicalshopuser.ui_layer.navigation.CartScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.HomeScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.OrderScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProductDetailScreenRoute
import com.tech.mymedicalshopuser.viewmodel.CartViewmodel
import com.tech.mymedicalshopuser.viewmodel.MedicalAuthViewmodel
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel
import kotlin.math.roundToInt

@Composable
fun CartScreen(navController: NavHostController, cartViewmodel: CartViewmodel,medicalAuthViewmodel: MedicalAuthViewmodel,orderViewmodel: OrderViewmodel) {
    var selectedItem by remember {
        mutableIntStateOf(1)
    }
    val context = LocalContext.current
    val cartList = cartViewmodel.cartItemsList
    val subTotalPrice by cartViewmodel.subTotalPrice.collectAsState()

    BackHandler {
        navController.navigate(HomeScreenRoute) {
            navController.graph.startDestinationRoute?.let { homeScreen ->
                popUpTo(homeScreen) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
                navController.popBackStack(
                    CartScreenRoute,
                    inclusive = true
                ) // Clear back stack and go to Screen1
            }
        }
    }
    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(WhiteGreyColor),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteGreyColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth()) {

                val previousRoute =
                    navController.previousBackStackEntry?.destination?.route

                Log.d("@prev", "CartScreen: $previousRoute")
                //when route comes from product detail screen then go for it other no need back button
                if (previousRoute != null) {
                    Row {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    navController.navigateUp()
                                }
                        )

                    }
                }
                Text(
                    text = "My Cart", style = TextStyle(
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    ), modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            Spacer(Modifier.height(8.dp))
            if (cartList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(520.dp)
                ) {
                    items(cartList) { product ->
                        CartItem(
                            count = product.product_count,
                            productItem = product,
                            itemOnClick = {
                                navController.navigate(
                                    ProductDetailScreenRoute(
                                        productName = product.product_name,
                                        productImage = product.product_image,
                                        productPrice = product.product_price,
                                        productRating = product.product_rating,
                                        productStock = product.product_stock,
                                        productDescription = product.product_description,
                                        productPower = product.product_power,
                                        productCategory = product.product_category,
                                        productExpiryDate = product.product_expiry_date,
                                        productId = product.product_id
                                    )
                                )
                            }, onDelete = {
                                cartViewmodel.removeItem(product)
                            }, increaseItem = {
                                cartViewmodel.updateItemCount(
                                    product,
                                    product.product_count + 1
                                )
                            }, decreaseItem = {
                                if (product.product_count > 1)
                                    cartViewmodel.updateItemCount(
                                        product,
                                        product.product_count - 1
                                    )
                            }
                        )
                    }
                }
            } else {
                val preloaderLottieComposition = rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(
                        R.raw.empty_cart_lottie
                    )
                )

                val preloaderProgress = animateLottieCompositionAsState(
                    composition = preloaderLottieComposition.value,
                    iterations = LottieConstants.IterateForever,
                    isPlaying = true
                )
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LottieAnimation(
                        composition = preloaderLottieComposition.value,
                        progress = preloaderProgress.value,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }

        if (cartList.isNotEmpty()) {
            CartPriceCard(
                subTotalPrice
            ) {
                val orderList = mutableListOf<MedicalOrderResponseItem>()
                for (productItem in cartList) {
                    val orderItem = MedicalOrderResponseItem(
                        product_category = productItem.product_category,
                        product_name = productItem.product_name,
                        product_price = productItem.product_price,
                        product_id = productItem.product_id,
                        product_quantity = productItem.product_count,
                        user_id = medicalAuthViewmodel.preferenceManager.getLoginUserId()!!,
                        user_name = medicalAuthViewmodel.preferenceManager.getLoginUserName()!!,
                        order_date = java.util.Date().toString(),
                        totalPrice = (productItem.product_count * productItem.product_price) + 10 + (subTotalPrice * 18 / 100).toInt(),
                        delivery_charge = 10,
                        tax_charge = (subTotalPrice * 18 / 100).toInt(),//according to gst 18%
                        subtotal_price = productItem.product_count * productItem.product_price,
                        isApproved = 0,
                        user_address = medicalAuthViewmodel.preferenceManager.getLoginAddress()!!,
                        user_email = medicalAuthViewmodel.preferenceManager.getLoginEmailId()!!,
                        user_mobile = medicalAuthViewmodel.preferenceManager.getLoginMobileNo()!!,
                        user_pinCode = medicalAuthViewmodel.preferenceManager.getLoginPinCode()!!,
                    )

                    orderList.add(orderItem)
                    Log.d(
                        "@OrderItem", "Product Category: ${orderItem.product_category}, " +
                                "Product Name: ${orderItem.product_name}, " +
                                "Product Price: ${orderItem.product_price}, " +
                                "Product ID: ${orderItem.product_id}, " +
                                "Product Quantity: ${orderItem.product_quantity}, " +
                                "User ID: ${orderItem.user_id}, " +
                                "User Name: ${orderItem.user_name}, " +
                                "Order Date: ${orderItem.order_date}, " +
                                "Total Price: ${orderItem.totalPrice}, " +
                                "Delivery Charge: ${orderItem.delivery_charge}, " +
                                "Tax Charge: ${orderItem.tax_charge}, " +
                                "Subtotal Price: ${orderItem.subtotal_price}, " +
                                "Is Approved: ${orderItem.isApproved}, " +
                                "User Address: ${orderItem.user_address}, " +
                                "User Email: ${orderItem.user_email}, " +
                                "User Mobile: ${orderItem.user_mobile}, " +
                                "User Pin Code: ${orderItem.user_pinCode}"
                    )
                }
                orderViewmodel.createOrder(orderList)
//                cartViewmodel.clearCart()
                navController.navigate(OrderScreenRoute)

            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            NavigationView(
                navController,
                selectedItem = selectedItem,
                onSelectedItem = {
                    selectedItem = it
                    Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                }
            )
        }

    }

}

@Composable
fun CartPriceCard(
    subTotalPrice: Float,
    checkOutClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 70.dp)
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val deliveryCharge = 10.00
            val taxCharge = (subTotalPrice*18/100).toFloat()
            val discount  = (subTotalPrice*5/100).toFloat()
            val totalPrice = (subTotalPrice+deliveryCharge+taxCharge-discount).roundToInt()

            TextRow(priceName = "Sub-Total", price = subTotalPrice.toString())
            TextRow(priceName = "Delivery Charge", price = deliveryCharge.toString())
            TextRow(priceName = "Tax Charge 18%", price = taxCharge.toString())
            TextRow(priceName = "Discount 5%", price = discount.toString())

            Spacer(modifier = Modifier.height(10.dp))

            Divider()
            Spacer(modifier = Modifier.height(10.dp))

            TextRow(priceName = "Total Price", price = totalPrice.toString())

            Spacer(modifier = Modifier.height(10.dp))
            CheckoutButton {
                checkOutClick()
            }
        }
    }
}

@Composable
fun CheckoutButton(
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenColor,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            elevation = ButtonDefaults.buttonElevation(4.dp)

        ) {
            Text(
                text = "Checkout", style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
            )
        }
    }

}

@Composable
fun TextRow(priceName: String, price: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = priceName, style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color.Black
            )
        )
        Text(
            text = "$price ₹", style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                color = GreenColor
            )
        )
    }
}

