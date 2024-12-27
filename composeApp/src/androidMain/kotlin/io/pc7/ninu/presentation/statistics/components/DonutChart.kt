package io.pc7.ninu.presentation.statistics.components

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
import io.pc7.ninu.domain.mapper.toDonutChartItem
import io.pc7.ninu.domain.model.lab.DonutChartItem
import io.pc7.ninu.presentation.lab.graphs.colors
import io.pc7.ninu.presentation.lab.screen.LabMainViewModel
import io.pc7.ninu.presentation.theme.custom.colorScheme
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

        Canvas(modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize(1f)
        ) {
            val canvasSizeInPx = with(density) { size.width }
            val radius = canvasSizeInPx / 2f

            var startAngle = 90f
            val arcData = mutableListOf<Triple<DonutChartItem, Offset, Brush>>()


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
                drawCircle(
                    brush = gradient,
                    radius = strokeWidth / 2,
                    center = offset
                )
            }
        }

        centerContent?.invoke()
    }
}



@Preview(widthDp = 500, heightDp = 500)
@Composable
private fun DonutChartPreview1() {

    val percentages = LabMainViewModel.initializeState().fragrances!!.mapIndexed { index, it -> it.toDonutChartItem(colors[index].first.toArgb().toLong(), colors[index].second.toArgb().toLong()) }
    Box(
        modifier = Modifier.fillMaxSize(1f)
    ) {
        DonutChart(
            percentages = percentages,
        )
    }

}


//@Composable
//fun DonutChart(
//    modifier: Modifier = Modifier,
//    percentages: List<Float>,
//    colors: List<Color>,
//    strokeWidth: Float = 150f,
//    centerContent: (@Composable () -> Unit)? = null
//) {
//
//    val density = LocalDensity.current
//    val strokeWidthDp = with(density) { (strokeWidth/2).toDp() }
//    Box(
//        modifier = modifier
//            .padding(strokeWidthDp)
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        fun calculateSweepAngle(percentage: Float): Float {
//            return percentage / 100 * 360
//        }
//
//
//        val textMeasurer = rememberTextMeasurer()
//
//        Canvas(modifier = Modifier
//            .aspectRatio(1f)
//            .fillMaxSize()
//
//        ) {
//            val canvasSizeInPx = with(density) { size.width }
//            val radius = canvasSizeInPx / 2f
//
//            var startAngle = 90f
//            val arcData = mutableListOf<Pair<Float, Offset>>()
//
//
//            percentages.forEachIndexed { index, percentage ->
//                val sweepAngle = calculateSweepAngle(percentage)
//
//                drawArc(
//                    color = colors[index],
//                    startAngle = startAngle,
//                    sweepAngle = sweepAngle,
//                    useCenter = false,
//                    style = Stroke(width = strokeWidth, miter = 20f),
//                )
//
//
//                val endAngle = startAngle + sweepAngle
//                val endAngleRadians = Math.toRadians(endAngle.toDouble())
//
//                val x = (radius + radius * cos(endAngleRadians)).toFloat()
//                val y = (radius + radius * sin(endAngleRadians)).toFloat()
//
//                arcData.add(Pair(percentage, Offset(x, y)))
//
//                startAngle += sweepAngle
//            }
//
//            val largestArc = arcData.maxByOrNull { it.first }!!
//            val largestIsAtEdge = arcData.indexOf(largestArc) == 0 || arcData.indexOf(largestArc) == arcData.size - 1
//
//            val remainingArcs = arcData.filter { it != largestArc }
//            val orderedArcs = (if (largestIsAtEdge) remainingArcs.reversed() else remainingArcs) + largestArc
//
//
//            orderedArcs.forEach { (percentage, offset) ->
//                drawCircle(
//                    color = Color(colorScheme.secondaryLight.toArgb()),
//                    radius = strokeWidth / 2,
//                    center = offset
//                )
//            }
//        }
//
//        centerContent?.invoke()
//    }
//}
