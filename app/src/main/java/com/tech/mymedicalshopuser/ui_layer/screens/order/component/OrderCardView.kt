package com.tech.mymedicalshopuser.ui_layer.screens.order.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui_layer.component.AsyncImageComponent

@Composable
fun OrderCardView(
    orderResponseItem: MedicalOrderResponseItem,
    onClick:()->Unit
) {

    Card(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth().background(Color.White)
            .padding(bottom = 8.dp, top = 8.dp)
            .padding(8.dp)
            .height(100.dp), border = BorderStroke(
            1.dp,
            GreenColor
        ), elevation = CardDefaults.cardElevation(4.dp), shape = RectangleShape
    ) {
        Row(
            modifier = Modifier.fillMaxSize().background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AsyncImageComponent(
                imageId = orderResponseItem.product_image_id,
                imageSize = 100.dp,
                padding = 8.dp,
                shape = RectangleShape
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextView(
                    text = orderResponseItem.product_name,
                    color = Color.Black,
                    fontSize = 24,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                TextView(
                    text = orderResponseItem.product_category,
                    color = Color.Black,
                    fontSize = 16,
                    fontWeight = FontWeight.Normal
                )
                Spacer(Modifier.height(8.dp))
                TextView(
                    text = orderResponseItem.order_date,
                    color = Color.Black,
                    fontSize = 16,
                    fontWeight = FontWeight.Normal
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun TextView(
    text: String,
    color: Color = Color.Black,
    fontSize: Int = 16,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily =FontFamily(Font(R.font.roboto_regular))
) {
    Text(
        text = text, style = TextStyle(
            color = color,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontFamily = fontFamily
        )
    )
}
