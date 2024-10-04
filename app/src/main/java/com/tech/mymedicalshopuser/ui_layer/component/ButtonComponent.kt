package com.tech.mymedicalshopuser.ui_layer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.mymedicalshopuser.R
import com.tech.mymedicalshopuser.ui.theme.GreenColor

@Composable
fun ButtonComponent(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(8.dp), color = GreenColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenColor,
            contentColor = Color.White,
            disabledContainerColor = GreenColor,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = text, style = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.roboto_medium))
        ))
    }
}