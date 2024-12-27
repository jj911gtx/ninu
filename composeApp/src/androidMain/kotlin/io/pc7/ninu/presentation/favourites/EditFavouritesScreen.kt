package io.pc7.ninu.presentation.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.model.perfume.NINUSelection
import io.pc7.ninu.domain.model.perfume.PerfumeUseData
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.other.CircleBracketOutlinedColor
import io.pc7.ninu.presentation.statistics.components.PerfumeBracket
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.util.mapper.toColor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime


@Composable
fun EditFavouritesScreen(
    navBack: () -> Unit,
    navToPerfumeInfo: () -> Unit,
    viewModel: EditFavouritesViewModel
) {

    EditFavouritesScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack,
        navToPerfumeInfo = navToPerfumeInfo
    )

}

@Composable
private fun EditFavouritesScreen(
    state: EditFavouritesState,
    action: (EditFavouritesAction) -> Unit,
    navBack: () -> Unit,
    navToPerfumeInfo: () -> Unit,
) {

    var activeEdit by remember { mutableStateOf<Int?>(null) }

    Column {
        ButtonTopLeftBack(
            onClick = navBack,
            text = "Edit NINU selections"
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {

            PerfumeSelectionBracket(ninuSelection = NINUSelection.N1, onEdit ={activeEdit = 1}, active = activeEdit == null || activeEdit == 1)
            PerfumeSelectionBracket(ninuSelection = NINUSelection.N2, onEdit ={activeEdit = 2}, active = activeEdit == null || activeEdit == 2)
            PerfumeSelectionBracket(ninuSelection = NINUSelection.N3, onEdit ={activeEdit = 3}, active = activeEdit == null || activeEdit == 3)
            PerfumeSelectionBracket(ninuSelection = NINUSelection.N,  onEdit ={activeEdit = 4}, active = activeEdit == null || activeEdit == 4)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Your favourites",
                style = MaterialTheme.typography.labelLarge,
                color = colorScheme.white
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(List(10){
                    PerfumeUseData(
                        name = "Lemon", time = LocalDateTime(date = LocalDate(1999, 5,5), time = LocalTime(12, 11,35))
                    )
                }){
                    it.PerfumeBracket(isSelected = false, onSelect = {
                        if(activeEdit != null) {
                            activeEdit = null
                        }else{
                            navToPerfumeInfo()
                        }
                    })
                }

            }
        }
    }

}


@Composable
private fun PerfumeSelectionBracket(
    modifier: Modifier = Modifier,
    ninuSelection: NINUSelection,
    active: Boolean,
    onEdit: () -> Unit
) {

    CardBracket(
        onClick = { /*TODO*/ },
        modifier = Modifier.graphicsLayer {
            // Adjust opacity based on the boolean value
            alpha = if (active) 1f else 0.5f
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            CircleBracketOutlinedColor(
                text = ninuSelection.toString(), borderColor = ninuSelection.toColor(),
                modifier = Modifier
                    .size(25.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "NINU Selection $ninuSelection",
                style = MaterialTheme.typography.labelLarge,
                color = colorScheme.white,
                modifier = Modifier
                    .weight(1f)
            )

            Text(text = "Edit",
                style = MaterialTheme.typography.displayLarge,
                color = colorScheme.secondaryLight1,
                modifier = Modifier.clickable(onClick = onEdit)
            )

        }
    }
}


@Preview
@Composable
private fun EditFavouritesScreenPreview() {
    NINUTheme {
        EditFavouritesScreen(
            state = EditFavouritesState(),
            action = {},
            navBack = { },
            navToPerfumeInfo = {}
        )
    }

}