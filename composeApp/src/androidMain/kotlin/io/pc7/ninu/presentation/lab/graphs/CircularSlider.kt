package io.pc7.ninu.presentation.lab.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.lab.screen.UpdateGraphPercentages
import kotlin.math.*

@Composable
fun rememberCircularSliderState(
    percentages: List<Int>,
    savePercentages: (List<Int>) -> Unit,
): MutableState<CircularSliderAdapter> {
    return remember {
        mutableStateOf(
            CircularSliderAdapter(
                percentages = percentages,
                savePercentages = savePercentages
            )
        )
    }
}

/**
 * @param startAngle radians
 * @param endAngle radians
 * */
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 200f,
    sliderColor: Color = Color(0xFFE57373),
    state: CircularSliderAdapter,
) {
    state.editorData.value?.let { editorData ->

        var selectedPoint by remember { mutableStateOf<Point?>(null) }

        fun updatePercentages(
            selectedPoint: Point?,
            state: CircularSliderAdapter,
            angle: Double
        ) {
            selectedPoint?.let {
                when (it) {
                    Point.START -> state.updatePercentages(UpdateGraphPercentages.Start(angle = angle))
                    Point.END -> state.updatePercentages(UpdateGraphPercentages.End(angle = angle))
                }
            }
        }

        Box(
            modifier = modifier
                .aspectRatio(1f)
                .pointerInput(state.editorData.value) {
                    detectTapGestures(
                        onPress = { position ->
                            val angle = getPointRadians(size, position)
                            selectedPoint = calculateDistanceToHandle(
                                angle,
                                editorData.startAngle,
                                editorData.endAngle
                            )
                            updatePercentages(selectedPoint, state, angle)
                            if (tryAwaitRelease()) {
                                selectedPoint = null
                            }
                        },
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        selectedPoint?.let { selectedPoint ->
                            val position = getPointRadians(size, change.position)
                            updatePercentages(selectedPoint, state, position)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f),
            ) {
                val radius = size.minDimension / 2
                val center = Offset(size.width / 2, size.height / 2)

                drawCircle(
                    color = colorScheme.primaryDarkest,
                    radius = radius,
                    style = Stroke(strokeWidth)
                )

                val endAngle = editorData.endAngle
                val startAngle = editorData.startAngle

                val sweepAngleRadians = if (endAngle >= startAngle) {
                    endAngle - startAngle
                } else {
                    2 * PI - (startAngle - endAngle)
                }

                val startAngleDegrees = Math.toDegrees(startAngle)
                val sweepAngleDegrees = Math.toDegrees(sweepAngleRadians)

                drawArc(
                    color = sliderColor,
                    startAngle = (startAngleDegrees - 90f).toFloat(),
                    sweepAngle = sweepAngleDegrees.toFloat(),
                    useCenter = false,
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(strokeWidth, cap = StrokeCap.Round),
                )

                drawHandleAtAngle(startAngle, radius, size, strokeWidth)
                drawHandleAtAngle(endAngle, radius, size, strokeWidth)

                drawPlusMinusSymbols(startAngle, endAngle, radius, size)
            }
        }
    }
}

private fun DrawScope.drawPlusMinusSymbols(
    startAngle: Double,
    endAngle: Double,
    radius: Float,
    size: Size,
) {
    val plusAngleOffset = Math.toRadians(20.0)
    val minusAngleOffset = Math.toRadians(14.0)

    val startXPlus = radius * cos(startAngle - plusAngleOffset - Math.PI / 2).toFloat() + size.width / 2
    val startYPlus = radius * sin(startAngle - plusAngleOffset - Math.PI / 2).toFloat() + size.height / 2

    val startXMinus = radius * cos(startAngle + minusAngleOffset - Math.PI / 2).toFloat() + size.width / 2
    val startYMinus = radius * sin(startAngle + minusAngleOffset - Math.PI / 2).toFloat() + size.height / 2







    val endXPlus = radius * cos(endAngle + plusAngleOffset - Math.PI / 2).toFloat() + size.width / 2
    val endYPlus = radius * sin(endAngle + plusAngleOffset - Math.PI / 2).toFloat() + size.height / 2

    val endXMinus = radius * cos(endAngle - minusAngleOffset - Math.PI / 2).toFloat() + size.width / 2
    val endYMinus = radius * sin(endAngle - minusAngleOffset - Math.PI / 2).toFloat() + size.height / 2

    drawContext.canvas.nativeCanvas.apply {
        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 80f
            textAlign = android.graphics.Paint.Align.CENTER
        }

        drawTextInBox("+", startXPlus, startYPlus, paint)
        drawTextInBox("-", startXMinus, startYMinus, paint)
        drawTextInBox("+", endXPlus, endYPlus, paint)
        drawTextInBox("-", endXMinus, endYMinus, paint)
    }
}

private fun android.graphics.Canvas.drawTextInBox(
    text: String,
    x: Float,
    y: Float,
    paint: android.graphics.Paint
) {
    val textWidth = paint.measureText(text)
    val textHeight = paint.descent() - paint.ascent()
    val boxPadding = 10f

    drawRect(
        x - textWidth / 2 - boxPadding,
        y - textHeight / 2 - boxPadding,
        x + textWidth / 2 + boxPadding,
        y + textHeight / 2 + boxPadding,
        android.graphics.Paint().apply {
            color = android.graphics.Color.TRANSPARENT
            style = android.graphics.Paint.Style.FILL
        }
    )

    drawText(text, x, y - (paint.ascent() + paint.descent()) / 2, paint)
}

fun calculateDistanceToHandle(
    angle: Double,
    startHandleAngle: Double,
    endHandleAngle: Double
): Point {
    val fullCircleRadians = (2 * PI).toFloat()

    val startDiff = abs(angle - startHandleAngle) % fullCircleRadians
    val startDistance = min(startDiff, fullCircleRadians - startDiff)

    val endDiff = abs(angle - endHandleAngle) % fullCircleRadians
    val endDistance = min(endDiff, fullCircleRadians - endDiff)

    return if (startDistance < endDistance) Point.START else Point.END
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHandleAtAngle(
    angle: Double,
    radius: Float,
    size: Size,
    strokeWidth: Float
) {
    val handleRadius = strokeWidth / 2

    val handleX = radius * cos(angle - Math.PI / 2).toFloat() + size.width / 2
    val handleY = radius * sin(angle - Math.PI / 2).toFloat() + size.height / 2

    drawCircle(
        color = colorScheme.white,
        radius = handleRadius / 2,
        center = Offset(handleX, handleY)
    )
}

private fun getPointRadians(
    size: IntSize,
    position: Offset,
): Double {
    val center = Offset(size.width / 2f, size.height / 2f)

    val deltaX = position.x - center.x
    val deltaY = position.y - center.y

    var radians = atan2(deltaY, deltaX)
    radians += Math.PI.toFloat() / 2

    return (radians + (2 * Math.PI).toFloat()) % (2 * Math.PI)
}

enum class Point {
    START,
    END;
}

@Preview(widthDp = 500, heightDp = 500)
@Composable
private fun CircularSliderPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var startAngle = 20.0
            var endAngle = 120.0
            startAngle = Math.toRadians(startAngle)
            endAngle = Math.toRadians(endAngle)
            CircularSlider(
                state = CircularSliderAdapter(percentages = listOf(10, 40), savePercentages = {})
            )
        }
    }
}


