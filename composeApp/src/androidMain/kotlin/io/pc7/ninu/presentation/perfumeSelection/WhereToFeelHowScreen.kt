package io.pc7.ninu.presentation.perfumeSelection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.perfumeSelection.WhereToSections
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.presentation.components.resource.Display
import kotlinx.coroutines.launch


data class WhereToFeelHowHeaderItem(
    val title: String,
    val onClick: () -> Unit,
)



@Composable
fun WhereToFeelHowScreen(
    headers: List<WhereToFeelHowHeaderItem>,
    items: Resource<ResultMy<PerfumeSelectionState.PerfumesSelections, DataError.Network>>,
    action: (PerfumeSelectionViewModel.Action) -> Unit,
    state: PerfumeSelectionState
) {



    val pagerState = rememberPagerState { WhereToSections.entries.size }

    val coroutine = rememberCoroutineScope()

    Column {
        Icon(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.textlogo),
            contentDescription = null,
            tint = colorScheme.white
        )
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            itemsIndexed(headers) { index, it ->
                Header(
                    text = it.title,
                    selected = pagerState.currentPage == index,
                    onClick = {
                        it.onClick()
                        coroutineScope.launch {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        items.Display { itemsResult ->
                HorizontalPager(
                    state = pagerState,
                ) { pageIndex ->

//                    var selectedIndex by rememberSaveable { mutableStateOf<Int?>(null) }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .weight(1f)
                    ){

                        itemsIndexed(itemsResult.perfumes[pageIndex]){ index, item ->

                            item.Display(selected = state.selectedItem != null && state.selectedItem == item,
                                onClick = {action(PerfumeSelectionViewModel.Action.SelectItem(item))},
                            )
                        }


                    }
                }


        }
    }
}

@Composable
fun Header(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Text(text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 32.sp),
        color = if(selected) colorScheme.white else colorScheme.primaryMedium,
        modifier = Modifier
            .clip(CircleShape)
            .padding(5.dp)
            .clickable(onClick = onClick)
    )
}