package com.tech.mymedicalshopuser.ui_layer.screens.order_screen.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor

@Composable
fun OrderCardView(
    orderResponseItem: MedicalOrderResponseItem,
) {

    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(500.dp), shape = RoundedCornerShape(16.dp), border = BorderStroke(
            2.dp,
            GreenColor
        ), elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    TextView(text = "Order Id: ")
                    TextView(text = "User Id: ")
                    TextView(text = "Product Id: ")
                    TextView(text = "Product Name: ")
                    TextView(text = "Product Category: ")
                    TextView(text = "User name: ")
                    TextView(text = "User Address: ")
                    TextView(text = "User PinCode: ")
                    TextView(text = "User Mobile: ")
                    TextView(text = "User Email: ")
                    TextView(text = "is Approved: ")
                    TextView(text = "Product Quantity: ")
                    TextView(text = "Product Price: ")
                    TextView(text = "Subtotal Price: ")
                    TextView(text = "Delivery Charge: ")
                    TextView(text = "Tax Charge: ")
                    TextView(text = "Total Price: ")
                    TextView(text = "Order Date: ")
                }
                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    TextView(text = orderResponseItem.order_id, fontSize = 13)
                    TextView(text = orderResponseItem.user_id, fontSize = 13)
                    TextView(text = orderResponseItem.product_id, fontSize = 13)
                    TextView(text = orderResponseItem.product_name)
                    TextView(text = orderResponseItem.product_category)
                    TextView(text = orderResponseItem.user_name)
                    TextView(text = orderResponseItem.user_address)
                    TextView(text = orderResponseItem.user_pinCode)
                    TextView(text = orderResponseItem.user_mobile)
                    TextView(text = orderResponseItem.user_email)
                    TextView(text = orderResponseItem.isApproved.toString())
                    TextView(text = orderResponseItem.product_quantity.toString())
                    TextView(text = orderResponseItem.product_price.toString())
                    TextView(text = orderResponseItem.subtotal_price.toString())
                    TextView(text = orderResponseItem.delivery_charge.toString())
                    TextView(text = orderResponseItem.tax_charge.toString())
                    TextView(text = orderResponseItem.totalPrice.toString())
                    TextView(text = orderResponseItem.order_date)
                }
            }
            Spacer(Modifier.height(4.dp))
            ButtonView(
                text = if(orderResponseItem.isApproved == 0) "Pending" else "Approved",
                color = if(orderResponseItem.isApproved == 0) Color.Red else GreenColor,
                enabled = true
            )
        }
    }
}

@Composable
fun TextView(
    text: String,
    color: Color = Color.Black,
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text, style = TextStyle(
            color = color,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontFamily = FontFamily(Font(R.font.roboto_regular))
        )
    )
}

@Composable
fun ButtonView(
    text: String,
    color: Color,
    enabled : Boolean,
) {
    Button(
        onClick = {

        },modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ), elevation = ButtonDefaults.buttonElevation(1.dp),
        enabled = enabled

    ) {
        Text(text = text)
    }

}