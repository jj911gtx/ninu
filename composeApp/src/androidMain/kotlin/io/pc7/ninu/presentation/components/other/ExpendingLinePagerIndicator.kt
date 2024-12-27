package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme


@Composable
fun ColumnScope.ExpendingLinePagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .align(Alignment.CenterHorizontally)
    ) {
        Canvas(modifier = Modifier.align(Alignment.Center)) {
            val spacing = 10.dp.toPx()
            val dotWidth = 10.dp.toPx()
            val dotHeight = 10.dp.toPx()
            val activeDotWidth = 50.dp.toPx()

            val totalWidth = (pagerState.pageCount - 1) * (dotWidth + spacing) + activeDotWidth
            var x = (size.width - totalWidth) / 2
            val y = center.y

            repeat(pagerState.pageCount) { i ->
                val posOffset = pagerState.pageOffset
                val dotOffset = posOffset % 1
                val current = posOffset.toInt()

                val factor = (dotOffset * (activeDotWidth - dotWidth))

                val calculatedWidth = when {
                    i == current -> activeDotWidth - factor
                    i - 1 == current || (i == 0 && posOffset > pagerState.pageCount - 1) -> dotWidth + factor
                    else -> dotWidth
                }

                val color = if (i == current) colorScheme.white else colorScheme.white

                drawIndicator(x, y, calculatedWidth, dotHeight, CornerRadius(100f, 100f), color)
                x += calculatedWidth + spacing
            }
        }
    }




}


private val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction


// To get scrolled offset from snap position
private fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

private fun DrawScope.drawIndicator(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: CornerRadius,
    color: Color
) {
    val rect = RoundRect(
        x,
        y - height / 2,
        x + width,
        y + height / 2,
        radius
    )
    val path = Path().apply { addRoundRect(rect) }
    drawPath(path = path, color = color)
}
