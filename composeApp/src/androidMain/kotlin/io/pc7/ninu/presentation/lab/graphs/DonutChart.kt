package io.pc7.ninu.presentation.lab.graphs


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.mapper.toDonutChartItem
import io.pc7.ninu.domain.model.lab.DonutChartItem
import io.pc7.ninu.presentation.lab.screen.LabMainViewModel
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    percentages: List<DonutChartItem>,
    strokeWidth: Float = 200f,
    onArcClick: (DonutChartItem) -> Unit,
    centerContent: (@Composable () -> Unit)? = null
) {
    val density = LocalDensity.current
    val strokeWidthDp = with(density){(strokeWidth/2).toDp()}

    Box(
        modifier = modifier
            .padding(strokeWidthDp),
        contentAlignment = Alignment.Center
    ) {
        fun calculateSweepAngle(percentage: Float): Float {
            return percentage / 100 * 360
        }


        val textMeasurer = rememberTextMeasurer()

        val clickableArcData = remember { mutableStateListOf<Triple<DonutChartItem, Float, Float>>() }
        val clickableCircleData = remember { mutableStateListOf<Pair<DonutChartItem, Offset>>() }

        Canvas(modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(1f)
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val dx = tapOffset.x - center.x
                    val dy = tapOffset.y - center.y
                    val distanceFromCenter = sqrt(dx * dx + dy * dy)

                    if (distanceFromCenter in (size.width / 2 - strokeWidth / 2)..(size.width / 2f + strokeWidth / 2)) {
                        val tapAngle = Math
                            .toDegrees(atan2(dy.toDouble(), dx.toDouble()))
                            .toFloat()
                            .let { if (it < -90) it + 360 else it }

                        clickableArcData.forEach { (item, startAngle, sweepAngle) ->
                            if (tapAngle in startAngle..(startAngle + sweepAngle)) {
                                onArcClick(item)
                            }
                        }
                    }

                    clickableCircleData.forEach { (item, offset) ->
                        val circleRadius = strokeWidth / 2
                        val distanceToCircleCenter = sqrt(
                            (tapOffset.x - offset.x).pow(2) + (tapOffset.y - offset.y).pow(2)
                        )

                        if (distanceToCircleCenter <= circleRadius) {
                            onArcClick(item)
                        }
                    }
                }
            }
        ) {
            val canvasSizeInPx = with(density) { size.width }
            val radius = canvasSizeInPx / 2f

            var startAngle = -90f
            val arcData = mutableListOf<Pair<DonutChartItem, Offset>>()

            clickableArcData.clear()
            clickableCircleData.clear()

            percentages.forEachIndexed { _, percentage ->
                val sweepAngle = calculateSweepAngle(percentage.percentage)
                val color = percentage.color

                drawArc(
                    color = Color(color),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, miter = 20f),
                )

                clickableArcData.add(Triple(percentage, startAngle, sweepAngle))

                val endAngle = startAngle + sweepAngle
                val endAngleRadians = Math.toRadians(endAngle.toDouble())

                val x = (radius + radius * cos(endAngleRadians)).toFloat()
                val y = (radius + radius * sin(endAngleRadians)).toFloat()

                arcData.add(Pair(percentage, Offset(x, y)))

                startAngle += sweepAngle
            }

            val largestArc:  Pair<DonutChartItem, Offset> = arcData.maxByOrNull { it.first.percentage }!!
            val largestIsAtEdge = arcData.indexOf(largestArc) == 0 || arcData.indexOf(largestArc) == arcData.size - 1

            val remainingArcs = arcData.filter { it != largestArc }
            val orderedArcs = (if (largestIsAtEdge) remainingArcs.reversed() else remainingArcs) + largestArc

            fun drawText(percentage: Float, offset: Offset) {
                if (percentage >= 5) {
                    val text = "${percentage.toInt()}%"

                    val fontSize = (strokeWidth / 8).sp

                    val textLayoutResult = textMeasurer.measure(
                        text = AnnotatedString(text),
                        style = TextStyle(
                            color = colorScheme.black,
                            fontSize = fontSize
                        )
                    )

                    // Draw text
                    drawText(
                        textLayoutResult = textLayoutResult,
                        brush = SolidColor(colorScheme.black),
                        topLeft = offset.copy(
                            x = offset.x - textLayoutResult.size.width / 2,
                            y = offset.y - textLayoutResult.size.height / 2
                        )
                    )
                }
            }

            orderedArcs.forEach { (percentage, offset) ->
                drawCircle(
                    color = Color(percentage.color),
                    radius = strokeWidth / 2,
                    center = offset
                )
                clickableCircleData.add(Pair(percentage, offset))
                drawText(percentage.percentage, offset)
            }
        }

        centerContent?.invoke()
    }
}




@Preview(widthDp = 500, heightDp = 500)
@Composable
private fun DonutChartPreview1() {
    val color = arrayOf(
        colorScheme.white.toArgb(),
        colorScheme.secondaryLight.toArgb(),
        colorScheme.secondaryDark.toArgb(),
    )
    val percentages = LabMainViewModel.initializeState().fragrances!!.map { it.toDonutChartItem(0xFFFFF00FF) }
    Box(
        modifier = Modifier.fillMaxSize(1f)
    ) {
        DonutChart(
            percentages = percentages,
            onArcClick = {}
        )
    }

}