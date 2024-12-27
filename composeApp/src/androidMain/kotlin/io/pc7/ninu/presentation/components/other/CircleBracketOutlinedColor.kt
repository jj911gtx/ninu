package io.pc7.ninu.presentation.components.other

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun CircleBracketOutlinedColor(
    modifier: Modifier = Modifier,
    text: String,
    borderColor: Color,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {

        val context = LocalContext.current
        Canvas(modifier = Modifier
            .matchParentSize()
        ) {
            val borderThickness = size.width * 0.03f
            val borderSpacing = size.width * 0.04f
            val center = Offset(size.width / 2, size.height / 2)

            drawCircle(
                color = borderColor,
                radius = size.width / 2f - borderThickness/2,
                center = center,
                style = Stroke(width = borderThickness),
            )

            drawCircle(
                color = colorScheme.primaryLightest,
                radius = size.width / 2f - (borderThickness * 1.5f + borderSpacing),
                center = center,
            )

            val textSize = size.width * 0.30f

            val typeface = Typeface.create(ResourcesCompat.getFont(context, R.font.montserrat_bold), Typeface.BOLD)

            val paint = Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                this.textSize = textSize
                color = colorScheme.black.toArgb()
                textAlign = android.graphics.Paint.Align.CENTER
                this.typeface = typeface
            }

            val textHeight = paint.descent() - paint.ascent()
            val textOffset = (textHeight / 2) - paint.descent()

            drawContext.canvas.nativeCanvas.drawText(text, center.x, center.y + textOffset, paint)

        }

    }
}


@Preview
@Composable
private fun CircleBracketOutlinedColorPreview() {
    NINUTheme {
        CircleBracketOutlinedColor(
            text = "1",
            borderColor = Color.Green
        )
    }

}