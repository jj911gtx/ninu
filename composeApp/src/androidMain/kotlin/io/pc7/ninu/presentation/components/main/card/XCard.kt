package io.pc7.ninu.presentation.components.main.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.NINUTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun XCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onXClick: () -> Unit = { },
    content: @Composable () -> Unit,
) {


    Box(
        modifier = modifier
            .clip(XCardShape())
            .background(color = colorScheme.primary)
            .then(
                onClick?.let {
                    Modifier.clickable(onClick = onClick)
                } ?: Modifier
            )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp, top = 5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close), contentDescription = null,
                tint = colorScheme.primaryLighter,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(30.dp)
                    .padding(bottom = 10.dp)
                    .clickable(onClick = onXClick)
            )

            content()
        }
    }





}


private class XCardShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val cornerRadius = 50f
            val cutoutRadius = 50f
            val cutoutWidth = 120f
            val cutoutHeight = 40f
            val cutoutAngle = 45f * PI / 180
            val cutoutSnapHeightFactor = 0.5f


            moveTo(0f, cornerRadius + cutoutHeight)
            arcTo(
                rect = Rect(0f, 0f + cutoutHeight, cornerRadius * 2, cornerRadius * 2 + cutoutHeight),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            val upperHeight = cutoutHeight * (1 - cutoutSnapHeightFactor)
            val upperRadius = upperHeight / (1 - cos(cutoutAngle))
            val upperWidth = upperRadius * sin(cutoutAngle)

            val lowerHeight = cutoutHeight * cutoutSnapHeightFactor
            val lowerRadius = lowerHeight / (1 - cos(cutoutAngle))
            val lowerWidth = lowerRadius * sin(cutoutAngle)

            val centerX = (size.width / 2).toFloat()
            val lowerOffsetX = (upperWidth + lowerWidth).toFloat()
            val lowerOffsetY = (cutoutHeight - lowerRadius).toFloat()
            val upperOffsetX = 0f
            val upperOffsetY = upperRadius.toFloat()

            lineTo(centerX - lowerOffsetX, cutoutHeight)
            arcToRad(
                rect = Rect(Offset(centerX - lowerOffsetX, lowerOffsetY), lowerRadius.toFloat()),
                startAngleRadians = (PI / 2).toFloat(),
                sweepAngleRadians = - cutoutAngle.toFloat(),
                forceMoveTo = false
            )
            arcToRad(
                rect = Rect(Offset(centerX, upperOffsetY), upperRadius.toFloat()),
                startAngleRadians = (PI + PI / 2 - cutoutAngle).toFloat(),
                sweepAngleRadians = 2 * cutoutAngle.toFloat(),
                forceMoveTo = false
            )
            arcToRad(
                rect = Rect(Offset(centerX + lowerOffsetX, lowerOffsetY), lowerRadius.toFloat()),
                startAngleRadians = (PI / 2 + cutoutAngle).toFloat(),
                sweepAngleRadians = - cutoutAngle.toFloat(),
                forceMoveTo = false
            )


            lineTo(size.width / 2 + cutoutHeight, 0f + cutoutHeight)
            arcTo(
                rect = Rect(size.width - cornerRadius * 2, 0f + cutoutHeight, size.width, cornerRadius * 2 + cutoutHeight),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(size.width, size.height - cornerRadius)
            arcTo(
                rect = Rect(size.width - cornerRadius * 2, size.height - cornerRadius * 2, size.width, size.height),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(cornerRadius, size.height)
            arcTo(
                rect = Rect(0f, size.height - cornerRadius * 2, cornerRadius * 2, size.height),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, cornerRadius)
        }
        return Outline.Generic(path)
    }
}




@Preview
@Composable
private fun XCardPreview() {
    NINUTheme {
        Box(modifier = Modifier.size(500.dp),
            contentAlignment = Alignment.Center) {
            XCard(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(200.dp, 300.dp),
            ) {
                Text(text = "fjkshgk skghks bg")
            }
        }
    }
}