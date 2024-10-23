package com.tech.mymedicalshopuser.ui_layer.screens.order_screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.binayshaw7777.kotstep.model.tabVerticalWithLabel
import com.binayshaw7777.kotstep.ui.vertical.VerticalStepper
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor
import com.tech.mymedicalshopuser.ui_layer.component.AsyncImageComponent
import com.tech.mymedicalshopuser.ui_layer.navigation.OrderDetailScreenRoute
import com.tech.mymedicalshopuser.ui_layer.screens.order_screen.component.OrderCardView
import com.tech.mymedicalshopuser.utils.GET_IMG_URL
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
            .background(WhiteGreyColor),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollSate)
                .padding(8.dp)
                .background(WhiteGreyColor)
        ) {
            Spacer(Modifier.height(8.dp))
            TopAppBar("Order Details") {
                navController.navigateUp()
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteGreyColor),
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
                            currentStep = 2,
                            trailingLabels = listOf(
                                { Text("Pending  ${orderData.order_date.substring(0, 10)}") },
                                { Text("Ordered Approved ${orderData.order_date.substring(0, 10)}") },
                                {
                                    if (orderData.shipped_date != "null")
                                        Text("Shipped  ${orderData.shipped_date.substring(0, 10)}")
                                },
                                {
                                    if (orderData.out_of_delivery_date != "null")
                                        Text(
                                            "Out of Delivery  ${
                                                orderData.out_of_delivery_date.substring(0,10)
                                            }"
                                        )
                                },
                                {
                                    if (orderData.delivered_date != "null")
                                        Text(
                                            "Delivered  ${
                                                orderData.delivered_date.substring(0, 10)
                                            }"
                                        )
                                },
                            )
                        ), modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteGreyColor),
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
                        text = "Pincode : ${orderData.user_pinCode}", style = TextStyle(
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
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteGreyColor),
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
                        items(orderList.filter { it.order_date == orderData.order_date }) {
                            OrderCardView(it) {
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
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteGreyColor),
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
