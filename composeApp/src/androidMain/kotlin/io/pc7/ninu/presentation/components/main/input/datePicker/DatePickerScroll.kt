package io.pc7.ninu.presentation.components.main.input.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.presentation.theme.NINUTheme
import kotlinx.coroutines.launch

data class MagneticScrollItem(
    val display: String,
    val value: Int,
)


@Composable
private fun MagneticScrollList(
    items: List<MagneticScrollItem>,

    ) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val currentVisibleItem by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }


    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    coroutineScope.launch {
                        val firstItemIndex = lazyListState.firstVisibleItemIndex
                        val firstItemOffset = lazyListState.firstVisibleItemScrollOffset

                        if (firstItemOffset > lazyListState.layoutInfo.viewportSize.height / 2) {
                            lazyListState.animateScrollToItem(firstItemIndex + 1)
                        } else {
                            lazyListState.animateScrollToItem(firstItemIndex)
                        }
                    }
                }
            }
    }

    Column {
        Button(onClick = {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(index = 100 - 1)
            }
        }) {
            Text(text = "Current item index: ${items[currentVisibleItem]}")
        }

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState),
            modifier = Modifier
                .height(150.dp)

        ) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            items(items) { items ->
                Text(
                    text = items.display,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}


@Composable
private fun MagneticScrollList(
    modifier: Modifier = Modifier,
    items: List<MagneticScrollItem>,
    onUpdate: (Int) -> Unit,
) {

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    val currentVisibleItem by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    LaunchedEffect(key1 = currentVisibleItem) {
        onUpdate(items[currentVisibleItem].value)
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    coroutineScope.launch {
                        val firstItemIndex = lazyListState.firstVisibleItemIndex
                        val firstItemOffset = lazyListState.firstVisibleItemScrollOffset

                        if (firstItemOffset > lazyListState.layoutInfo.viewportSize.height / 2) {
                            lazyListState.animateScrollToItem(firstItemIndex + 1)
                        } else {
                            lazyListState.animateScrollToItem(firstItemIndex)
                        }
                    }
                }
            }
    }


    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {

    }

    LazyColumn(
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState),
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .background(Color.Green.copy(alpha = 0.1f))
    ) {
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
        itemsIndexed(items) { index, item ->
            Text(
                text = item.display,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = if(index == currentVisibleItem ) 32.sp else 24.sp),
                modifier = Modifier
                    .padding(16.dp)

            )
        }
        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}






@Preview
@Composable
private fun DatePickerPreview() {
    NINUTheme {
        var value by remember {
            mutableStateOf(1)
        }
        Column {
            Text(text = "value is: ${value}")
            MagneticScrollList(
                modifier = Modifier,
                List<Int>(30){it + 1}.map { MagneticScrollItem(it.toString(), it) },
                onUpdate = {value = it}
            )
        }

    }
    
}
