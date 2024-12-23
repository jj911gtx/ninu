package io.pc7.ninu.presentation.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.buttons.DefaultButton
import io.pc7.ninu.presentation.components.other.BatteryComponent
import io.pc7.ninu.presentation.components.other.CircleBracketOutlinedColor
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.home.HomeScreenAction
import io.pc7.ninu.presentation.home.HomeScreenEvent
import io.pc7.ninu.presentation.home.HomeScreenState
import io.pc7.ninu.presentation.home.HomeScreenViewModel
import io.pc7.ninu.presentation.main.components.InfoBracket
import io.pc7.ninu.presentation.main.components.InfoBracketType
import io.pc7.ninu.presentation.main.components.LinePagerIndicator
import io.pc7.ninu.presentation.theme.NINUTheme
import ninu.other.home.HomeScreenViewModelAndroid
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    pair: () -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel<HomeScreenViewModelAndroid>().viewModel,
) {


    if(false){
        HomeScreen(
            state = viewModel.state.collectAsState().value,
            action = {viewModel.action(it)},

        )
    }
    else{
        HomeScreenConnect(
            state = viewModel.state.collectAsState().value,
            action = {viewModel.action(it)},
            pair = pair
        )
    }


}


@Composable
private fun HomeScreen(
    state: HomeScreenState,
    action: (HomeScreenAction) -> Unit,

) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Box(

            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .height(IntrinsicSize.Max)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colorScheme.black)
                            .fillMaxHeight()
                    ) {
                        val percentage = 0.3f

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
//                                .padding(1.dp),
                                    ,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            BatteryComponent(
                                percentage = percentage,
                                modifier = Modifier
                                    .size(30.dp)
                            )

                            Text(
                                text = "${(percentage * 100).toInt()}%",
                                color = colorScheme.white
                            )
                        }

                    }
                }
                Image(painter = painterResource(id = R.drawable.device), contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

        }


        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                NINUSelectionItem(
                    number = 1
                )
                NINUSelectionItem(
                    number = 2
                )
                NINUSelectionItem(
                    number = 3
                )
                NINUSelectionItem(
                    number = null
                )
            }
        }

        item {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    text = "Edit NINU selections",
                    style = MaterialTheme.typography.labelMedium,
                    color = colorScheme.white
                )
            }
        }



        item {
            val list = listOf<Triple<String, String, String?>>(
                Triple("NINU Tips & Tricks", "How To Properly Apply Fragrance", null),
                Triple("NINU Tips & Tricks", "How To Properly Apply Fragrance", null),
                Triple("NINU Tips & Tricks", "How To Properly Apply Fragrance", null),
            )
            val pager = rememberPagerState { list.size }
            Box {
                HorizontalPager(
                    state = pager,
                    pageSpacing = 20.dp
                ) {
                    InfoBracket(
                        type = InfoBracketType.TIP,
                        smallTitle = list[it].first,
                        title = list[it].second,
                        description =  list[it].third,
                        pager = pager
                    )

                }
                LinePagerIndicator(pager)
            }
        }
        item {
            val xxx = Triple("NINU Tips & Tricks", "How To Properly Apply Fragrance", null)

            InfoBracket(
                type = InfoBracketType.TIP,
                smallTitle = xxx.first,
                title = xxx.second,
                description =  xxx.third,
                readMore = true
            )
        }
    }

}




@Composable
private fun HomeScreenConnect(
    state: HomeScreenState,
    action: (HomeScreenAction) -> Unit,
    pair: () -> Unit,
) {

    Box(
        Modifier.fillMaxSize()
    ) {
        Icon(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.textlogo),
            contentDescription = null,
            tint = colorScheme.white
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Image(painter = painterResource(id = R.drawable.device), contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                
            )    
            DefaultButton(
                onClick = pair
            ) {
                Text(text = "Pair your device",
                    style = MaterialTheme.typography.displayLarge)
            }
        }
        
    }



}


@Composable
private fun RowScope.NINUSelectionItem(
    number: Int? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .weight(1f)
    ) {
        CircleBracketOutlinedColor(
            text = if(number == null) "N" else number.toString(),
            borderColor = when(number){
                1 -> Color.Yellow
                2 -> Color.Cyan
                3 -> Color.Blue
                else -> colorScheme.white

            },
        )
        Text(text = if(number != null)
                    "NINU selection ${number}"
                else "Last UPLOADED",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            color = colorScheme.white
        )
    }


    
}

@Preview
@Composable
private fun HomeScreenConnectPreview() {
    NINUTheme {
        HomeScreen(
            state = HomeScreenViewModel.initialize(),
            action = {}
        )
    }
}