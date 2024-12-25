package io.pc7.ninu.presentation.statistics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.model.perfume.PerfumeUseData
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.statistics.components.BracketRowText
import io.pc7.ninu.presentation.statistics.components.DonutChart
import io.pc7.ninu.presentation.statistics.components.PerfumeBracket
import io.pc7.ninu.presentation.theme.NINUTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.androidx.compose.koinViewModel


@Composable
fun StatisticsScreen(
    navBack: () -> Unit,
    navToPerfumeStatus: () -> Unit,
    viewModel: StatisticsViewModel = koinViewModel<StatisticsViewModeAndroid>().viewModel
) {

    StatisticsScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack,
        navToPerfumeStatus = navToPerfumeStatus
    )
}

@Composable
private fun StatisticsScreen(
    state: StatisticsState,
    action: (StatisticsAction) -> Unit,
    navBack: () -> Unit,
    navToPerfumeStatus: () -> Unit,
) {

    Column {
        ButtonTopLeftBack(
            onClick = navBack,
            text = "Usage Statistics"
        )


        ScrollableColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            CardBracket(onClick = { /*TODO*/ }) {
                BracketRowText(text = "Average weekly use", rightText = "6,5")
            }
            CardBracket(onClick = { /*TODO*/ }) {
                BracketRowText(text = "Days above average", rightText = "10")
            }

            var trendingExtended by remember { mutableStateOf(false) }
            CardBracket(
                onClick = { trendingExtended = !trendingExtended}
            ) {
                BracketRowText(text = "Trending", rightText = "+10%")
                AnimatedVisibility(
                    visible = trendingExtended,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {


                    }
                }
            }

            Graph()
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Recent usage",
                style = MaterialTheme.typography.labelLarge,
                color = colorScheme.white,
                modifier = Modifier
                    .padding(start = 10.dp)
            )




            List(20){
                PerfumeUseData(
                    name = "Lemon", time = LocalDateTime(date = LocalDate(1999, 5,5), time = LocalTime(12, 11,35))
                )
            }.forEach {
                it.PerfumeBracket(isSelected = false, onSelect = navToPerfumeStatus)
            }


        }


    }

}


@Composable
private fun Graph(

) {
    CardBracket(
        onClick = {},
        modifier = Modifier
            .fillMaxSize()

    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Most used",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp)
                )
                Text(
                    text = "Elegant",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        DonutChart(
            percentages = listOf(20f,50f, 30f),
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(30.dp)
        ){
            @Composable
            fun Item(
                name: String,
                percentage: Int
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)//TODO
                        .size(15.dp)
                    )
                    Text(text = name,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = "${percentage}%",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Item(name = "Elegant", percentage = 13)
                Item(name = "Elegant", percentage = 13)
                Item(name = "Elegant", percentage = 13)
            }
        }
    }
}






@Preview
@Composable
private fun StatisctisScreenPreview() {
    NINUTheme {
        StatisticsScreen(
            state = StatisticsState(),
            action = {},
            navBack = {},
            navToPerfumeStatus = {}
        )
    }

}