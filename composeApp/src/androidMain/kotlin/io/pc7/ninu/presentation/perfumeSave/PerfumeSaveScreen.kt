package io.pc7.ninu.presentation.perfumeSave

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainAction
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainEvent
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainState
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainViewModel
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeTextContentScreen
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PerfumeSaveScreen(
    navBack: () -> Unit,
    viewModel: PerfumeMainViewModel,
    navToLab: () -> Unit,
    navHome: () -> Unit
) {


    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            is PerfumeMainEvent.SaveRespond -> {
                Toast.makeText(
                    context,
                    when(event.success){
                        true -> "Write Successful"
                        false -> "Write error"
                    },
                    Toast.LENGTH_LONG
                ).show()
                if(event.success){
                    navHome()
                }
            }
        }
    }

    PerfumeSaveScreen(
        state = viewModel.state.collectAsState().value,
        action = { viewModel.action(it) },
        navBack = navBack,
        navToLab = navToLab
    )



}
@Composable
private fun PerfumeSaveScreen(
    state: PerfumeMainState,
    action: (PerfumeMainAction) -> Unit,
    navBack: () -> Unit,
    navToLab: () -> Unit,
) {


    PerfumeTextContentScreen(
        state = state,
        action = action,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Save your fusion",
                    color = colorScheme.primaryLightest,
                    style = MaterialTheme.typography.bodyMedium
                )

                NINUTextField(
                    value =state.name,
                    onUpdate = { action(PerfumeMainAction.OnUpdateName(it)) },
                    placeholderText = "Name your fragrance"

                )

            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "Chose icon", color = colorScheme.primaryLightest,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(
                        R.drawable.icon_briefcase,
                        R.drawable.icon_baggage_claim,
                        R.drawable.icon_bed,
                        R.drawable.icon_apple,
                        R.drawable.icon_car,
                        R.drawable.icon_ball,
                    ).forEach {
                        IconDisplay(
                            iconId = it,
                            onClick = { action(PerfumeMainAction.OnSelectIcon(it)) },
                            isSelected = state.selectedIcon == it
                        )
                    }
                }
            }
        },
        navBack = navBack,
        navToLab = navToLab,
        buttonText = "Save"
    )

}


@Composable
private fun IconDisplay(
    iconId: Int,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Icon(painter = painterResource(id = iconId), contentDescription = null,
        tint = if(isSelected) colorScheme.secondaryDark2 else colorScheme.white,
        modifier = Modifier
            .size(20.dp)
            .clickable(onClick = onClick)
    )

}


@Preview()
@Composable
private fun LabSaveScreenPreview() {
    NINUTheme {
        PerfumeSaveScreen(
            state = PerfumeMainViewModel.initializeState(),
            action = {},
            navBack = { /*TODO*/ },
            navToLab = {}
        )
    }
}



