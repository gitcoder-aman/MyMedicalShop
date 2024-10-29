package com.tech.mymedicalshopuser.ui_layer.screens.order

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.binayshaw7777.kotstep.model.tabVerticalWithLabel
import com.binayshaw7777.kotstep.ui.vertical.VerticalStepper
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui_layer.component.AsyncImageComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.OrderDetailScreenRoute
import com.tech.mymedicalshopuser.ui_layer.screens.order.component.OrderCardView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Locale

@Composable
fun OrderDetailScreen(
    orderData: MedicalOrderResponseItem,
    orderList: List<MedicalOrderResponseItem>,
    navController: NavHostController
) {

    val scrollSate = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.White)
        ) {
            Spacer(Modifier.height(8.dp))
            TopAppBar(headerName = "Order Details", isCloseIcon = true) {
                navController.navigateUp()
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(8.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollSate)
                    .padding(8.dp)
                    .background(Color.White)
            ) {

                ProductDetailCard(orderData)

                Spacer(Modifier.height(8.dp))
                ShippingDetailCard(orderData)

                Spacer(Modifier.height(8.dp))
               val orderItemInThisCart =  orderList.filter { it.order_date == orderData.order_date }
                    .filter { it.order_id != orderData.order_id }
                if(orderItemInThisCart.isNotEmpty()) {

                    OrderItemInThisOrderCard(orderItemInThisCart) {
                        val jsonFormat = Json {
                            ignoreUnknownKeys = true
                        }   //for ignore all integer value
                        val orderDateInJson = jsonFormat.encodeToString(orderData)
                        val orderListJson = jsonFormat.encodeToString(orderList)
                        navController.navigate(
                            OrderDetailScreenRoute(
                                orderData = orderDateInJson,
                                orderList = orderListJson
                            )
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }
                PriceDetailCard(orderData)


            }

        }
    }
}

@Composable
fun PriceTextView(
    text: String,
    price: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text, style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_light)),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
        )
        Text(
            text = stringResource(R.string.rs) + " " + price, style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_light)),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
        )
    }
}

@Composable
fun PriceDetailCard(orderData: MedicalOrderResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Price Details", style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(8.dp))
            PriceTextView(
                text = "Selling Price",
                price = orderData.product_price.toString()
            )
            Spacer(Modifier.height(8.dp))
            PriceTextView(
                text = "Subtotal Price",
                price = orderData.subtotal_price.toString()
            )
            Spacer(Modifier.height(8.dp))
            PriceTextView(
                text = "Extra Discount",
                price = orderData.discount_price
            )
            Spacer(Modifier.height(8.dp))
            PriceTextView(
                text = "Delivery Charge",
                price = orderData.delivery_charge.toString()
            )
            Spacer(Modifier.height(8.dp))
            PriceTextView(
                text = "Tax Charge",
                price = orderData.tax_charge.toString()
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(8.dp))

            PriceTextView(
                text = "Total Price",
                price = orderData.totalPrice.toString()
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Cash on Delivery : " + stringResource(R.string.rs) + " " + "${orderData.totalPrice}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )

        }
    }
}

@Composable
fun ProductDetailCard(orderData: MedicalOrderResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Order ID - ${orderData.order_id}", style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = orderData.product_name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }, style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Category - ${
                            orderData.product_category.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            }
                        }",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Qty - ${orderData.product_quantity}", style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.rs) + " ${orderData.subtotal_price}",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        )
                    )

                }
                AsyncImageComponent(
                    imageId = orderData.product_image_id,
                    imageSize = 100.dp,
                    padding = 8.dp,
                    shape = RectangleShape
                )
            }
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(16.dp))
            VerticalStepper(
                style = tabVerticalWithLabel(
                    totalSteps = 5,
                    currentStep = orderData.order_status.toInt(),
                    trailingLabels = listOf(
                        { Text("Pending  ${orderData.order_date.substring(0, 10)}") },
                        {
                            Text(
                                "Ordered Approved ${
                                    orderData.order_date.substring(
                                        0,
                                        10
                                    )
                                }"
                            )
                        },
                        {
                            Text(
                                "Shipped  ${
                                    if (orderData.shipped_date != "null")
                                        orderData.shipped_date.substring(0, 10) else ""
                                }"
                            )
                        },
                        {
                            Text(
                                "Out of Delivery  ${
                                    if (orderData.out_of_delivery_date != "null")
                                        orderData.out_of_delivery_date.substring(0, 10) else ""
                                }"
                            )
                        },
                        {
                            Text(
                                "Delivered  ${
                                    if (orderData.delivered_date != "null")
                                        orderData.delivered_date.substring(0, 10) else ""
                                }"
                            )
                        },
                    )
                ), modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ShippingDetailCard(orderData: MedicalOrderResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Shipping Details", style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(8.dp))

            Text(
                text = orderData.user_name, style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Address : ${orderData.user_address}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Street : ${orderData.user_street}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Mobile No : ${orderData.user_mobile}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "City : ${orderData.user_city}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "State : ${orderData.user_state}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "PinCode : ${orderData.user_pinCode}", style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
fun OrderItemInThisOrderCard(
    orderItemInThisCart: List<MedicalOrderResponseItem>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Order Items in This Order", style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(orderItemInThisCart) {
                    OrderCardView(it) {
                        onClick()
                    }
                }
            }
        }
    }
}