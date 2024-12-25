package io.pc7.ninu.presentation.statistics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.statistics.components.BracketRowText
import io.pc7.ninu.presentation.theme.NINUTheme

@Composable
fun PerfumeStatusScreen(
    navBack: () -> Unit,
    viewModel: PerfumeStatusViewModel// = koinViewModel()
) {

    PerfumeStatus(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack
    )

}

@Composable
private fun PerfumeStatus(
    state: PerfumeStatusState,
    action: (PerfumeStatusAction) -> Unit,
    navBack: () -> Unit,
) {

    ScrollableColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        ButtonTopLeftBack(onClick = navBack,
            text = "Perfume status"
        )


        CardBracket(
            onClick = {}
        ){
            BracketRowText(
                text = "Fragrance set in use",
                rightText = "AURA"
            )
        }

        //  --------------------        DESCRIPTION        ----------------
//        CardBracket(
//            onClick = {},
//        ){
//            Column() {
//                var extended by remember { mutableStateOf(false) }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
//                ) {
//                    Text(
//                        text = "Set description",
//                        style = MaterialTheme.typography.labelLarge,
//                        color = colorScheme.white,
//                        modifier = Modifier
//                            .padding(start = 10.dp)
//                            .weight(1f)
//                    )
//                    Text(
//                        text = "Show more",
//                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 15.sp),
//                        color = colorScheme.secondaryLight1,
//                        modifier = Modifier
//                            .padding(end = 5.dp)
//                            .clickable { extended = !extended }
//                    )
//                }
//                AnimatedVisibility(
//                    visible = extended,
//                    enter = expandVertically() + fadeIn(),
//                    exit = shrinkVertically() + fadeOut()
//                ) {
//                    Text(
//                        text = "Strikes the balance between lighter fresh scents and softer earth notes, a category that traditionally is described as unisex.",
//                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier
//                            .padding(horizontal = 10.dp)
//                            .padding(bottom = 5.dp)
//                    )
//                }
//            }
//
//        }
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier.weight(1f))

        //Graph
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            GraphColumn(
                name = "spiritus",
                percentage = 33f
            )
            GraphColumn(
                name = "spiritus",
                percentage = 59f
            )
            GraphColumn(
                name = "spiritus",
                percentage = 11f
            )
        }
    }
}


@Composable
fun RowScope.GraphColumn(
    name: String,
    percentage: Float,

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(1f)
    ) {
        Text(
            text = name.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.white,
        )
        extracted(percentage)
        Text(
            text = "Opened date\n${"22.9.2022"}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = (MaterialTheme.typography.bodyMedium.fontSize.value - 1).sp),
            textAlign = TextAlign.Center,
            color = colorScheme.primaryMedium
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Fragrance description",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = (MaterialTheme.typography.labelMedium.fontSize.value + 1).sp),
            color = colorScheme.secondaryLight1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable{
                    //TODO
                }
        )

    }
}

@Composable
private fun extracted(percentage: Float) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .background(colorScheme.primary)
            .height(300.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(percentage / 100)
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
            text = "${percentage.toInt()}%",
            style = MaterialTheme.typography.headlineLarge,
            color = colorScheme.white,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
        )

    }
}

@Preview(widthDp = 100, heightDp = 400)
@Composable
private fun GraphColumnPreview() {

    NINUTheme {
        Surface {
            Row {
                GraphColumn(
                    name = "Spiritus", percentage = 74f,
                )

            }

        }
    }

}


@Preview(heightDp = 600)
@Composable
private fun PerfumeStatusPreview() {
    NINUTheme{
        PerfumeStatus(
            state = PerfumeStatusState(),
            action = {},
            navBack = {}
        )
    }
}