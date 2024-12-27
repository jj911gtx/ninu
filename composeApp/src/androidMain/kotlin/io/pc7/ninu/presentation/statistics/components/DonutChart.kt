package io.pc7.ninu.presentation.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import io.pc7.ninu.presentation.theme.custom.colorScheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    percentages: List<Float>,
    strokeWidth: Float = 150f,
    centerContent: (@Composable () -> Unit)? = null
) {

    val density = LocalDensity.current
    val strokeWidthDp = with(density) { (strokeWidth/2).toDp() }
    Box(
        modifier = modifier
            .padding(strokeWidthDp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        fun calculateSweepAngle(percentage: Float): Float {
            return percentage / 100 * 360
        }


        val textMeasurer = rememberTextMeasurer()

        Canvas(modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()

        ) {
            val canvasSizeInPx = with(density) { size.width }
            val radius = canvasSizeInPx / 2f

            var startAngle = 90f
            val arcData = mutableListOf<Pair<Float, Offset>>()


            percentages.forEachIndexed { _, percentage ->
                val sweepAngle = calculateSweepAngle(percentage)
                val color = colorScheme.secondaryLight

                drawArc(
                    color = Color(color.toArgb()),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, miter = 20f),
                )


                val endAngle = startAngle + sweepAngle
                val endAngleRadians = Math.toRadians(endAngle.toDouble())

                val x = (radius + radius * cos(endAngleRadians)).toFloat()
                val y = (radius + radius * sin(endAngleRadians)).toFloat()

                arcData.add(Pair(percentage, Offset(x, y)))

                startAngle += sweepAngle
            }

            val largestArc = arcData.maxByOrNull { it.first }!!
            val largestIsAtEdge = arcData.indexOf(largestArc) == 0 || arcData.indexOf(largestArc) == arcData.size - 1

            val remainingArcs = arcData.filter { it != largestArc }
            val orderedArcs = (if (largestIsAtEdge) remainingArcs.reversed() else remainingArcs) + largestArc


            orderedArcs.forEach { (percentage, offset) ->
                drawCircle(
                    color = Color(colorScheme.secondaryLight.toArgb()),
                    radius = strokeWidth / 2,
                    center = offset
                )
            }
        }

        centerContent?.invoke()
    }
}
