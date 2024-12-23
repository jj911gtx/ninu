package io.pc7.ninu.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.main.components.slidingLineTransition

@Composable
fun BoxScope.LinePagerIndicator(pager: PagerState) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 10.dp)
    ) {
        val indicatorWidth = 40.dp
        val indicatorHeight = 4.dp
        val indicatorSpacing = 15.dp

        val density = LocalDensity.current
        val distanceInDp = indicatorWidth + indicatorSpacing
        val distanceInPx = with(density) { distanceInDp.toPx() }
        Row(
            horizontalArrangement = Arrangement.spacedBy(indicatorSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pager.pageCount) {
                Box(
                    modifier = Modifier
                        .size(width = indicatorWidth, height = indicatorHeight)
                        .background(
                            color = colorScheme.white.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                )
            }
        }
        Box(
            Modifier
                .slidingLineTransition(pager, distance = distanceInPx)
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(
                    color = colorScheme.white,
                    shape = RoundedCornerShape(3.dp),
                )
        )
    }
}