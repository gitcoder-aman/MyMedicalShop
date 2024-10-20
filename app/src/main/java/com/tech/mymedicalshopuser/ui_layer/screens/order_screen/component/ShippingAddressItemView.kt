package com.tech.mymedicalshopuser.ui_layer.screens.order_screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.local.entity.AddressEntity
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.ui.theme.LightGreenColor

@Composable
fun ShippingAddressItemView(address: AddressEntity, selectedAddress: Boolean, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(50.dp)
            .width(150.dp)
            .shadow(shape = RoundedCornerShape(8.dp), elevation = 2.dp)
            .background(if (selectedAddress) GreenColor else LightGreenColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = address.address,
            style = MaterialTheme.typography.titleLarge,
            color = if (selectedAddress) Color.Black else Color.Gray,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            modifier = Modifier.align(Alignment.Center).padding(4.dp)
        )
    }

}