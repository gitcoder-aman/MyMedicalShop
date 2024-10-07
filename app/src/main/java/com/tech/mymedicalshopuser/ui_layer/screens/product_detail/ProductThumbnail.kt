package com.tech.mymedicalshopuser.ui_layer.screens.product_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.mymedicalshopuser.R

@Composable
fun ProductThumbnail(
    onBackClick:()->Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
//        AsyncImage(
//            model = exerciseImage.toString(),
//            contentDescription = null, contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//        )
        Image(
            painter = painterResource(R.drawable.med_1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), contentScale = ContentScale.Crop
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
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

//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 300.dp, start = 8.dp, end = 8.dp)
//        ) {
//            Spacer(modifier = Modifier.height(8.dp))
//            TextComponent(
//                text = "$workoutType Workout",
//                fontSize = 12.sp,
//                font = R.font.sans_medium,
//                color = colorResource(
//                    id = R.color.orangeColor
//                )
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            TextComponent(text = exerciseName.toString(), fontSize = 24.sp, font = R.font.sans_bold)
//            Spacer(modifier = Modifier.height(14.dp))
//            TextComponent(
//                text = exerciseListSize + ".109 kcl",
//                fontSize = 12.sp,
//                font = R.font.sans_light
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            TextComponent(text = "Exercise", fontSize = 16.sp, font = R.font.sans_medium)
//            Spacer(modifier = Modifier.height(8.dp))
//
//
//        }
    }

}
