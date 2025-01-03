package io.pc7.ninu.presentation.statistics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.charts.BarChart
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

    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier
    ) {
        ButtonTopLeftBack(onClick = navBack,
            text = stringResource(R.string.fragrance_status)
        )


        CardBracket(
            onClick = {}
        ){
            BracketRowText(
                text = stringResource(R.string.fragrance_set_in_use),
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


        //Graph
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier

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
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
    ) {

        Text(
            text = name.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.white,
        )
        BarChart(
            percentage = percentage / 100,
            backgroundColor = colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${stringResource(R.string.opened_date)}\n${"22.9.2022"}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = (MaterialTheme.typography.bodyMedium.fontSize.value - 1).sp),
            textAlign = TextAlign.Center,
            color = colorScheme.primaryMedium
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.fragrance_description),
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