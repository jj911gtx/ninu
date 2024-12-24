package io.pc7.ninu.presentation.perfumeSelection



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import io.pc7.ninu.data.network.model.navigation.NavigatePerfumeMain
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionDisplay
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.main.navigation.MainNavigationRoutes
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PerfumeSelectionScreen(
    navToPerfume: (MainNavigationRoutes.PerfumeDetails) -> Unit,
    viewModel: PerfumeSelectionViewModel
){

    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            is PerfumeSelectionViewModel.Event.Navigate -> navToPerfume(MainNavigationRoutes.PerfumeDetails(
                fragrances = event.perfumeData.fragrances,
                id = event.perfumeData.id,
                name = event.perfumeData.name
            ))
        }
    }

    PerfumeSelectionScreen(
        state = viewModel.state.collectAsState().value,
        headers = viewModel.headers,
        action = viewModel::action,

    )
}

@Composable
private fun PerfumeSelectionScreen(
    state: PerfumeSelectionState,
    headers: List<String>,
    action: (PerfumeSelectionViewModel.Action) -> Unit,
) {
    var selectedSection by remember {
        mutableStateOf(0)
    }

    state.selectedItem?.let {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable {}
            )
        }
    }

    WhereToFeelHowScreen(
        headers = headers.mapIndexed { index, title ->
            WhereToFeelHowHeaderItem(
                title = title,
                onClick = {
                    selectedSection = index
                }
            )
        },
        items = state.perfumes,
        action = action,
        state = state,

    )

}



@Preview
@Composable
private fun WhereToScreenPreview() {
    NINUTheme {
        PerfumeSelectionScreen(
            state = PerfumeSelectionState(),
            headers = listOf(""),
            action = {}
        )

    }

}