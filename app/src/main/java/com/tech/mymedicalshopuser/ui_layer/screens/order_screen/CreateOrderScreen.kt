package com.tech.mymedicalshopuser.ui_layer.screens.order_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.domain.model.ClientChoiceModelEntity
import com.tech.mymedicalshopuser.local.entity.AddressEntity
import com.tech.mymedicalshopuser.local.viewmodel.RoomAddressViewModel
import com.tech.mymedicalshopuser.local.viewmodel.RoomCartViewModel
import com.tech.mymedicalshopuser.state.screen_state.AddAddressScreenState
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor
import com.tech.mymedicalshopuser.ui_layer.navigation.AddressScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.CompletedOrderScreenRoute
import com.tech.mymedicalshopuser.ui_layer.navigation.ProductDetailScreenRoute
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.component.OrderItemView
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.component.ShippingAddressItemView
import com.tech.mymedicalshopuser.utils.PreferenceManager
import com.tech.mymedicalshopuser.utils.calculateDeliveryCharge
import com.tech.mymedicalshopuser.utils.calculateTaxCharge
import com.tech.mymedicalshopuser.utils.totalPriceCalculate
import com.tech.mymedicalshopuser.viewmodel.OrderViewmodel

@Composable
fun CreateOrderScreen(
    cartList: List<ClientChoiceModelEntity>,
    orderViewmodel: OrderViewmodel,
    roomAddressViewModel: RoomAddressViewModel,
    subTotalPrice: Float,
    roomCartViewModel: RoomCartViewModel,
    navController: NavController
) {

    val addressList by roomAddressViewModel.addressList.collectAsState()
    var selectedAddressIndex by remember { mutableStateOf(-1) } // -1 indicates no selection
    val context = LocalContext.current
    val preferenceManager = PreferenceManager(context)

    Log.d("@createOrder", "CreateOrderScreen: ${cartList.size}")

    val createOrderResponseStatus = orderViewmodel.createOrder.collectAsState()

    when {
        createOrderResponseStatus.value.isLoading -> {
            CircularProgressIndicator(color = GreenColor)
        }

        createOrderResponseStatus.value.data != null -> {
            Log.d("@create_order", "OrderScreen: ${createOrderResponseStatus.value.data!!.message}")
            Log.d("@create_order", "OrderScreen: ${createOrderResponseStatus.value.data!!.status}")
        }

        createOrderResponseStatus.value.error != null -> {
            Log.d("@create_order", "OrderScreen: ${createOrderResponseStatus.value.error}")
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White), contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TopAppBar("Billing") {
                navController.navigateUp()
            }
            Spacer(Modifier.height(16.dp))
            Divider(thickness = 2.dp, color = LightGreenColor)
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Payment Methods",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium))
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "This Medical App does not support virtual payments.You can pay when you receive your order",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular))
            )
            Spacer(Modifier.height(4.dp))
            Divider(thickness = 2.dp, color = LightGreenColor)
            Spacer(Modifier.height(16.dp))
            ShippingAddressTopBar {
                navController.navigate(AddressScreenRoute)
            }
            Spacer(Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(addressList.size) { index ->
                    ShippingAddressItemView(
                        selectedAddress = selectedAddressIndex == index,
                        address = addressList[index]
                    ) {
                        selectedAddressIndex = index
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            Divider(thickness = 2.dp, color = LightGreenColor)
            Spacer(Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(cartList) { cart ->
                    OrderItemView(
                        cart
                    ) {
                        navController.navigate(
                            ProductDetailScreenRoute(
                                productName = cart.product_name,
                                productId = cart.product_id,
                                productCategory = cart.product_category,
                                productPrice = cart.product_price,
                                productImageId = cart.product_image_id,
                                productDescription = cart.product_description,
                                productPower = cart.product_power,
                                productStock = cart.product_stock,
                                productRating = cart.product_rating,
                                productExpiryDate = cart.product_expiry_date
                            )
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Divider(thickness = 2.dp, color = LightGreenColor)
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(2.dp, LightGreenColor),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Prices",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )

                    Text(
                        text = stringResource(
                            R.string.rs,
                            totalPriceCalculate(subTotalPrice)
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (selectedAddressIndex > -1) {
                        val orderListReady = createOrderOneByOne(
                            addressList[selectedAddressIndex],
                            cartList,
                            preferenceManager
                        )
                        orderViewmodel.createOrder(orderListReady)
                        roomCartViewModel.deleteAllCartList()
                        navController.navigate(CompletedOrderScreenRoute)
                    } else {
                        Toast.makeText(context, "Please Select Address.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = GreenColor,
                    contentColor = Color.Black
                ), shape = RoundedCornerShape(4.dp), enabled = if(subTotalPrice > 0) true else false
            ) {
                Text(
                    text = "Place Order",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            }


        }

    }
}

fun createOrderOneByOne(
    address: AddressEntity,
    cartList: List<ClientChoiceModelEntity>,
    preferenceManager: PreferenceManager
): MutableList<MedicalOrderResponseItem> {
    val orderList = mutableListOf<MedicalOrderResponseItem>()
    for (productItem in cartList) {
        val orderItem = MedicalOrderResponseItem(
            product_category = productItem.product_category,
            product_name = productItem.product_name,
            product_price = productItem.product_price,
            product_id = productItem.product_id,
            product_quantity = productItem.product_count,
            user_id = preferenceManager.getLoginUserId()!!,
            user_name = address.fullName,
            order_date = java.util.Date().toString(),
            totalPrice = totalPriceCalculate(productItem.product_price.toFloat()),
            delivery_charge = calculateDeliveryCharge(productItem.product_price.toFloat()).toInt(),
            tax_charge = calculateTaxCharge(productItem.product_price.toFloat()).toInt(),//according to gst 18%
            subtotal_price = productItem.product_count * productItem.product_price,
            isApproved = preferenceManager.getApprovedStatus(),
            user_address = address.address,
            user_email = preferenceManager.getLoginEmailId()!!,
            user_mobile = address.phoneNo,
            user_pinCode = address.pinCode,
            product_image_id = productItem.product_image_id
        )

        orderList.add(orderItem)
    }
    return orderList
}

@Composable
fun ShippingAddressTopBar(
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "Shipping Address",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium))
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clickable {
                    onClick()
                }
        )
    }

}

@Composable
fun TopAppBar(
    headerName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .clickable { onClick() }
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = headerName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium))
        )
    }
}
