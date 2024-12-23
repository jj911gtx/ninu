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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.lab.screen.UpdateGraphPercentages
import kotlin.math.*


@Composable
fun rememberCircularSliderState(
    percentages: List<Int>,
    savePercentages: (List<Int>) -> Unit,
):MutableState<CircularSliderAdapter> {
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
    sliderColor: Color = Color(0xFFE57373),//TODO pass this variable
//    startAngle: Double,
//    endAngle: Double,
    state: CircularSliderAdapter,
//    action: (LabMainAction.UpdatePercentages) -> Unit,
) {

    state.editorData.value?.let { editorData ->


        /**
         * true     -> end
         * false    -> start */
        var selectedPoint by remember {
            mutableStateOf<Point?>(null)
        }


        fun updatePercentages(
            selectedPoint: Point?,
            state: CircularSliderAdapter,
            angle: Double
        ) {
            selectedPoint?.let {
                when (it) {
                    Point.START -> {
                        state.updatePercentages(UpdateGraphPercentages.Start(angle = angle))
                    }

                    Point.END ->
                        state.updatePercentages(UpdateGraphPercentages.End(angle = angle))
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
                    detectDragGestures { change, dragAmount ->
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
                    color = Color.Transparent,
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
                    startAngle = (startAngleDegrees - 90f).toFloat(), // Adjust the angle to start at the top (12 o'clock position)
                    sweepAngle = sweepAngleDegrees.toFloat(),
                    useCenter = false,
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(strokeWidth, cap = StrokeCap.Round),
                )

                drawHandleAtAngle(startAngle, radius, size, strokeWidth)

                drawHandleAtAngle(endAngle, radius, size, strokeWidth)

            }

        }
    }
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

enum class Point{
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
        ){
            var startAngle =  20.0
            var endAngle = 120.0
            startAngle = Math.toRadians(startAngle)
            endAngle = Math.toRadians(endAngle)
            CircularSlider(
                state = CircularSliderAdapter(percentages = listOf(10,40), savePercentages = {})

            )

        }

    }
}


