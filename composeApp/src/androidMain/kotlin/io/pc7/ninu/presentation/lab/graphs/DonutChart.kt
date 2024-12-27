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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
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
    strokeWidth: Float = 150f,
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
            val arcData = mutableListOf<Triple<DonutChartItem, Offset, Brush>>()

            clickableArcData.clear()
            clickableCircleData.clear()

            percentages.forEachIndexed { _, percentage ->
                val sweepAngle = calculateSweepAngle(percentage.percentage)
                
                val startAngleRadians = Math.toRadians(startAngle.toDouble())
                val endAngleRadians = Math.toRadians((startAngle + sweepAngle).toDouble())

                // Calculate the start and end points of the gradient
                val startX = (radius + radius * cos(startAngleRadians)).toFloat()
                val startY = (radius + radius * sin(startAngleRadians)).toFloat()
                val endX = (radius + radius * cos(endAngleRadians)).toFloat()
                val endY = (radius + radius * sin(endAngleRadians)).toFloat()

                val gradientColors = listOf(
                    Color(percentage.colors.first),
                    Color(percentage.colors.second)
                )

                // Create a gradient brush for the arc
                val gradientBrush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY)
                )

                // Draw the arc with the gradient
                drawArc(
                    brush = gradientBrush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                clickableArcData.add(Triple(percentage, startAngle, sweepAngle))

                // Calculate end point for the dot
                val x = (radius + radius * cos(endAngleRadians)).toFloat()
                val y = (radius + radius * sin(endAngleRadians)).toFloat()

                arcData.add(Triple(percentage, Offset(x, y), gradientBrush))

                startAngle += sweepAngle
            }

            // Sort and draw the dots with matching gradients
            val largestArc = arcData.maxByOrNull { it.first.percentage }!!
            val largestIsAtEdge = arcData.indexOf(largestArc) == 0 || arcData.indexOf(largestArc) == arcData.size - 1
            val remainingArcs = arcData.filter { it != largestArc }
            val orderedArcs = (if (largestIsAtEdge) remainingArcs.reversed() else remainingArcs) + largestArc

            orderedArcs.forEach { (percentage, offset, gradient) ->
                // Draw circle with the matching gradient
                drawCircle(
                    brush = gradient,
                    radius = strokeWidth / 2,
                    center = offset
                )
                clickableCircleData.add(Pair(percentage, offset))
                drawText(percentage.percentage, offset, textMeasurer, percentage.colors)
            }
        }

        centerContent?.invoke()
    }
}

// Helper function to draw text
private fun DrawScope.drawText(
    percentage: Float,
    offset: Offset,
    textMeasurer: TextMeasurer,
    colors: Pair<Long, Long>
) {
    if (percentage >= 5) {
        val text = "${percentage.toInt()}%"
        val fontSize = (150 / 7).sp

        // Determine text color based on background color
        val isWhiteBackground = Color(colors.first) == colorScheme.white && 
                               Color(colors.second) == colorScheme.white
        val textColor = if (isWhiteBackground) colorScheme.black else colorScheme.white

        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(text),
            style = TextStyle(
                color = textColor,
                fontSize = fontSize
            )
        )

        // Draw text
        drawText(
            textLayoutResult = textLayoutResult,
            brush = SolidColor(textColor),
            topLeft = offset.copy(
                x = offset.x - textLayoutResult.size.width / 2,
                y = offset.y - textLayoutResult.size.height / 2
            )
        )
    }
}



val colors = arrayOf(
    Pair(colorScheme.secondaryDark, colorScheme.secondaryLight),
    Pair(colorScheme.secondaryLight1, colorScheme.secondaryLight1),
    Pair(colorScheme.white, colorScheme.white),
)

@Preview(widthDp = 500, heightDp = 500)
@Composable
private fun DonutChartPreview1() {

    val percentages = LabMainViewModel.initializeState().fragrances!!.mapIndexed {index, it -> it.toDonutChartItem(colors[index].first.toArgb().toLong(), colors[index].second.toArgb().toLong()) }
    Box(
        modifier = Modifier.fillMaxSize(1f)
    ) {
        DonutChart(
            percentages = percentages,
            onArcClick = {}
        )
    }

}