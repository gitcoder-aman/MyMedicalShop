package com.tech.mymedicalshopuser.ui_layer.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.tech.mymedicalshopuser.ui.theme.GreenColor

@Composable
fun MulticolorText(
    firstText : String,secondText : String,
    onClick : ()->Unit
) {

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(firstText)
        }
        withStyle(style = SpanStyle(color = GreenColor)) {
            append(secondText)
        }
    }
    Text(text = annotatedString,modifier = Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    })
}