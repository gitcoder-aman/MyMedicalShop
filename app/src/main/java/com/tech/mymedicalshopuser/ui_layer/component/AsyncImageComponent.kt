package com.tech.mymedicalshopuser.ui_layer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.tech.mymedicalshopuser.ui.theme.GreenColor
import com.tech.mymedicalshopuser.utils.GET_IMG_URL

@Composable
fun AsyncImageComponent(
     imageId : String,
     imageSize : Dp,
     padding : Dp = 0.dp,
     shape : Shape,
     modifier : Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = GET_IMG_URL + imageId, modifier = modifier
            .padding(padding)
            .size(imageSize)
            .shadow(
                elevation = 2.dp,
                shape = shape
            ), contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .background(Color.LightGray) // Placeholder color
                    .align(Alignment.Center) // Centering the content
            ) {
                CircularProgressIndicator( // Loading indicator
                    modifier = Modifier.align(Alignment.Center),
                    color = GreenColor
                )
            }
        },
        contentDescription = null
    )

}