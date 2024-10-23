package com.tech.mymedicalshopuser.ui_layer.screens.order_screen.component


import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.data.response.order.MedicalOrderResponseItem
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.utils.GET_IMG_URL

//TextView(text = "Order Id: ")
//TextView(text = "User Id: ")
//TextView(text = "Product Id: ")
//TextView(text = "Product Name: ")
//TextView(text = "Product Category: ")
//TextView(text = "Product Image Id: ")
//TextView(text = "User name: ")
//TextView(text = "User Address: ")
//TextView(text = "User PinCode: ")
//TextView(text = "User Mobile: ")
//TextView(text = "User Email: ")
//TextView(text = "is Approved: ")
//TextView(text = "Product Quantity: ")
//TextView(text = "Product Price: ")
//TextView(text = "Subtotal Price: ")
//TextView(text = "Delivery Charge: ")
//TextView(text = "Tax Charge: ")
//TextView(text = "Total Price: ")
//TextView(text = "Order Date: ")
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
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 8.dp)
            .padding(8.dp)
            .height(100.dp), border = BorderStroke(
            1.dp,
            GreenColor
        ), elevation = CardDefaults.cardElevation(4.dp), shape = RectangleShape
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            SubcomposeAsyncImage(
                model = GET_IMG_URL + orderResponseItem.product_image_id, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RectangleShape
                    ), contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.LightGray) // Placeholder color
                            .align(Alignment.Center) // Centering the content
                    ) {
                        CircularProgressIndicator( // Loading indicator
                            modifier = Modifier.align(Alignment.Center), color = GreenColor
                        )
                    }
                },
                contentDescription = null
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

@Composable
fun ButtonView(
    text: String,
    color: Color,
    enabled: Boolean,
) {
    Button(
        onClick = {

        }, modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ), elevation = ButtonDefaults.buttonElevation(1.dp),
        enabled = enabled

    ) {
        Text(text = text)
    }

}