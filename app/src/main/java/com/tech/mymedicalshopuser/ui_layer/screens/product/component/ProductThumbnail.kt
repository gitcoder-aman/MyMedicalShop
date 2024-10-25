package com.tech.mymedicalshopuser.ui_layer.screens.product.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.tech.mymedicalshopuser.ui_layer.component.AsyncImageComponent

@Composable
fun ProductThumbnail(
    productImageId : String,
    onBackClick:()->Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImageComponent(
            imageId = productImageId,
            imageSize = 100.dp,
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp)
                .background(Color.White, shape = CircleShape)
                .padding(4.dp)
                .size(35.dp)
                .align(Alignment.TopStart)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBackClick()
                }
        )
        Icon(
            imageVector = if (true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "",
            modifier = Modifier
                .padding(end = 12.dp, top = 12.dp)
                .background(Color.White, shape = CircleShape)
                .padding(4.dp)
                .align(Alignment.TopEnd)
                .size(35.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                },
            tint = if (false) Color.Red else Color.Black
        )
    }

}
