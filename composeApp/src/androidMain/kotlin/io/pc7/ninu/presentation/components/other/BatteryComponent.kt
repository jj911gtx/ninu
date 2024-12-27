package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.util.getPathDataFromSvg
import io.pc7.ninu.presentation.theme.NINUTheme


/**
 * percentage 0f to 1f
 * width
* */
@Composable
fun BatteryComponent(
    modifier: Modifier = Modifier,
    percentage: Float,

) {
    val context = LocalContext.current
    val stringPath = getPathDataFromSvg(context, R.drawable.icon_battery)
    val path: Path = PathParser().parsePathString(
        stringPath!!
    ).toPath()



    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentWidth()
            .aspectRatio(2.5f)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val borderOffset = canvasHeight * 0.095f
            val endOffset = canvasWidth * 0.125f

            val rectLeft = borderOffset
            val rectTop = borderOffset
            val rectRight = canvasWidth - endOffset
            val rectBottom = canvasHeight - borderOffset

            val batteryWidth = (rectRight - rectLeft) * percentage

            drawRect(
                color = when (percentage) { //TODO colors
                    in 0f..0.2f -> Color.Red
                    in 0.2f..0.4f -> Color.Yellow
                    in 0.4f..1f -> Color.Green
                    else -> Color.Red
                },
                topLeft = Offset(rectLeft, rectTop),
                size = Size(
                    width = batteryWidth,
                    height = rectBottom - rectTop
                )
            )

            val pathBounds = path.getBounds()
            val scaleX = canvasWidth / pathBounds.width
            val scaleY = canvasHeight / pathBounds.height

            scale(scaleX = scaleX, scaleY = scaleY, pivot = Offset(0f, 0f)) {
                drawPath(path, color = colorScheme.white)
            }
        }
    }
}





@Preview
@Composable
private fun BatteryComponentPreview() {
    val content = LocalContext.current
    NINUTheme {
        BatteryComponent(
            modifier = Modifier
                .size(550.dp),
            percentage = 1f
        )
    }
}
