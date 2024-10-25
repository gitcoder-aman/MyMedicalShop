package com.tech.mymedicalshopuser.ui_layer.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tech.mymedicalshopuser.ui.theme.WhiteGreyColor

@Composable
fun ShimmerEffectForHome(
    modifier: Modifier,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
) {

    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )
    BoxForHome(brush,modifier)

}

@Composable
fun BoxForHome(
    brush: Brush,
    modifier: Modifier
) {

    Column(
        modifier  = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp).background(brush)
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp).background(brush)
        )
        Spacer(Modifier.height(16.dp))
        Row {
            Box(
                modifier = Modifier.size(80.dp).background(shape = CircleShape,brush = brush)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(80.dp).background(shape = CircleShape,brush = brush)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(80.dp).background(shape = CircleShape,brush = brush)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(80.dp).background(shape = CircleShape,brush = brush)
            )
        }
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(190.dp).background(brush)
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(190.dp).background(brush)
        )
    }
}

