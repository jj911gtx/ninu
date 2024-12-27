package io.pc7.ninu.presentation.components.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    percentage: Float,
    backgroundColor: Color,
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .background(backgroundColor)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(percentage)
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFC5837D),
                            Color(0xFF70443B)
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )
        Text(
            text = "${(percentage * 100).toInt()}%",
            style = MaterialTheme.typography.headlineLarge,
            color = colorScheme.white,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
        )
    }
    
    
}


@Preview
@Composable
private fun BarChartPreview() {
    NINUTheme {
        BarChart(
            modifier = Modifier.fillMaxHeight(),
            percentage = 0.4f,
            backgroundColor = colorScheme.primary
        )
    }
}