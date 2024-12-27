package io.pc7.ninu.presentation.lab

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.domain.mapper.toDonutChartItem
import io.pc7.ninu.domain.model.lab.LabFragrance
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.DefaultButton
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.lab.graphs.CircularSlider
import io.pc7.ninu.presentation.lab.graphs.DonutChart
import io.pc7.ninu.presentation.lab.graphs.rememberCircularSliderState
import io.pc7.ninu.presentation.lab.screen.LabMainAction
import io.pc7.ninu.presentation.lab.screen.LabMainEvents
import io.pc7.ninu.presentation.lab.screen.LabMainState
import io.pc7.ninu.presentation.lab.screen.LabMainViewModel
import io.pc7.ninu.presentation.theme.NINUTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun LabMainScreen(
    navBack: () -> Unit,
    navNext: (Array<Fragrance>, Int) -> Unit,
    viewModel: LabMainViewModel = koinViewModel<LabMainViewModelAndroid>().viewModel,
) {

    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            is LabMainEvents.NavigateNext -> navNext(event.fragrances, event.intensity)
        }
    }

    LabMainScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.onAction(it)},
        navBack = navBack,
    )

}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabMainScreen(
    state: LabMainState,
    action: (LabMainAction) -> Unit,
    navBack: () -> Unit,
) {

    val fragrances = state.fragrances ?: emptyList()
    var circularSliderState = rememberCircularSliderState(
        percentages = fragrances.map { it.percentage },
        savePercentages = {
            action(LabMainAction.OnSavePercentages(it))
        }
    )

    val colors = remember {
        arrayOf(
            colorScheme.secondaryDark1,
            colorScheme.secondaryLight,
            colorScheme.white,
        )
    }


    Box {
        ScrollableColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween//spacedBy(20.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = navBack)
                        .align(Alignment.CenterStart)
                        .size(20.dp),
                    tint = colorScheme.white
                )
                Text(
                    text = "NINU lab",
                    color = colorScheme.white,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            //        var selectedPerfumeIndex by remember {
            //            mutableStateOf<Int?>(null)
            //        }


            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                fragrances.forEachIndexed { index, cartridge ->
                    PerfumeContentCard(
                        fragrance = cartridge,
                        onClick = {
                            circularSliderState.value.selectPerfume(index)
                        },
                        selected = index == circularSliderState.value.editorData.value?.index,
                        color = colors[index]
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.CenterHorizontally),
            ) {


                if (circularSliderState.value.editorData.value?.index != null) {
                    CircularSlider(
                        modifier = Modifier
                            .align(Alignment.Center),
                        state = circularSliderState.value
                    )
                } else {
                    DonutChart(
                        modifier = Modifier
                            .align(Alignment.Center),
                        percentages = fragrances.mapIndexed() { index, item -> item.toDonutChartItem(colors[index].toArgb().toLong()) },
                        onArcClick = { donutItem ->
                            val cartridge = fragrances.find { it.sku == donutItem.id }
                            circularSliderState.value.selectPerfume(
                                fragrances.indexOf(
                                    cartridge
                                )
                            )

                        }
                    )
                }

            }



            Column(

            ) {
                Text(
                    text = stringResource(R.string.intensity),
                    color = colorScheme.white,
                    modifier = Modifier
                )
                BoxWithConstraints(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(y = (-5).dp)
                        .height(100.dp) // Increased height to ensure space for dividers
                ) {
                    val sliderWidth = maxWidth*1.05f

                    val interactionSource = remember { MutableInteractionSource() }

                    Slider(
                        value = state.intensity.toFloat(),
                        onValueChange = {
                            action(LabMainAction.OnUpdateIntensity(it.toInt()))
                        },
                        valueRange = 1f..4f,
                        steps = 2,
                        colors = SliderDefaults.colors(
                            activeTrackColor = colorScheme.primaryMedium,
                            inactiveTrackColor = colorScheme.primaryMedium,
                            thumbColor = colorScheme.white
                        ),
                        interactionSource = interactionSource,
                        track = { _ ->
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                drawLine(
                                    start = Offset(x = 0.dp.toPx(), y = 0.dp.toPx()),
                                    end = Offset(x = size.width, y = 0.dp.toPx()),
                                    strokeWidth = 10f.dp.toPx(),
                                    color = colorScheme.primaryMedium,
                                    cap = StrokeCap.Round
                                )
                            }
                        },
                        thumb = { _ ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = colorScheme.white,
                                        shape = CircleShape
                                    )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                    @Composable
                    fun MyVerticalDivider(
                        modifier: Modifier = Modifier,
                    ) {
                        VerticalDivider(
                            color = colorScheme.white,
                            modifier = modifier
                                .height(15.dp)
                        )

                    }

                    MyVerticalDivider(
                        modifier = Modifier
                            .offset(
                                x = sliderWidth * (1f / 3f),
                                y = 10.dp
                            )
                            .align(Alignment.TopStart)
                    )
                    MyVerticalDivider(
                        modifier = Modifier
                            .offset(
                                x = -(sliderWidth * (1f / 3f)),
                                y = 10.dp
                            )
                            .align(Alignment.TopEnd)
                    )
                    MyVerticalDivider(
                        modifier = Modifier
                            .offset(
                                x = sliderWidth * (1f / 3f),
                                y = -10.dp
                            )
                            .align(Alignment.BottomStart)
                    )
                    MyVerticalDivider(
                        modifier = Modifier
                            .offset(
                                x = -(sliderWidth * (1f / 3f)),
                                y = -10.dp
                            )
                            .align(Alignment.BottomEnd)
                    )

                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            //        Spacer(modifier = Modifier.weight(1f))








        }
        DefaultButtonText(
            onClick = {
                if (circularSliderState.value.editorData.value != null) {
                    circularSliderState.value.editorData.value = null
                } else {
                    action(LabMainAction.LoadScent)
                }
            },
            text = if (circularSliderState.value.editorData.value != null) stringResource(R.string.confirm) else stringResource(R.string.load_scent),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    top = 10.dp
                )
                .fillMaxWidth(1f),
        )
    }


}


@Composable
fun PerfumeContentCard(
    modifier: Modifier = Modifier,
    fragrance: LabFragrance,
    color: Color,
    onClick: () -> Unit,
    selected: Boolean
) {

    Box(
        modifier = modifier.then(
            if(selected){
                Modifier.scale(scaleX = 1.03f, scaleY = 1.01f)
            }else{ Modifier }
        )
    ){
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if(selected) colorScheme.primary else colorScheme.primary.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(5.dp),

            onClick = onClick
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (selected) {
                            Modifier.padding(vertical = 5.dp)
                        } else {
                            Modifier
                        }
                    )
                ,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Canvas(modifier = Modifier
                        .padding(end = 10.dp)
                        .size(10.dp),
                        contentDescription = "Circle"
                    ) {
                        drawCircle(
                            color = color,
                            radius = size.minDimension / 2,
                        )
                    }
                    Row(modifier = Modifier) {
                        Text(
                            text = fragrance.name,
                            modifier = Modifier,
                            color = colorScheme.white
                        )
                        if (selected){
                            Text(text = "   ${stringResource(R.string.active)}",
                                color = colorScheme.secondaryLight
                            )
                        }
                    }
                }


                Text(
                    text = "${fragrance.percentage} %",
                    color = colorScheme.white,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                )



            }
        }
    }


}






@Preview
@Composable
private fun LabMainScreenPreview() {
    NINUTheme{
        LabMainScreen(
            state = LabMainViewModel.initializeState(),
            action = {},
            navBack = {},
        )
    }
}








