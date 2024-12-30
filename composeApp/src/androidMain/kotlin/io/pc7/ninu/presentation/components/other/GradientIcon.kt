package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.graphicsLayer
import io.pc7.ninu.R

@Composable
fun GradientIcon() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.icon_ai),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(alpha = 0.99f) // To allow blend modes
                .drawWithContent {
                    // Create a gradient brush
                    val gradient = Brush.linearGradient(
                        colors = listOf(Color.Red, Color.Blue)
                    )
                    // Draw the gradient
                    drawWithGradient(gradient)
                }
        )
    }
}

// Helper extension function to draw content with gradient
private fun ContentDrawScope.drawWithGradient(brush: Brush) {
    drawContent()
    drawRect(brush = brush)
}

@Preview(showBackground = true)
@Composable
fun PreviewGradientIcon() {
    GradientIcon()
}
