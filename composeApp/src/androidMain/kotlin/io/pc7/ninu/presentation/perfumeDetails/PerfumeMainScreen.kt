package io.pc7.ninu.presentation.perfumeDetails

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainAction
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainEvent
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainState
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainViewModel
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeTextContentScreen
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PerfumeMainScreen(
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
                        true -> context.getString(R.string.write_successful)
                        false -> context.getString(R.string.write_error)
                    },
                    Toast.LENGTH_LONG
                ).show()
                if(event.success){
                    navHome()
                }
            }
        }
    }

    PerfumeMainScreen(
        state = viewModel.state.collectAsState().value,
        action = { viewModel.action(it) },
        navBack = navBack,
        navToLab = navToLab
    )



}
@Composable
private fun PerfumeMainScreen(
    state: PerfumeMainState,
    action: (PerfumeMainAction) -> Unit,
    navBack: () -> Unit,
    navToLab: () -> Unit,
) {


    PerfumeTextContentScreen(
        state = state,
        action = action,
        navBack = navBack,
        navToLab = navToLab,
        buttonText = stringResource(R.string.load_scent)
    ){

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(text = "Meeting with the president",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 32.sp),
                color = colorScheme.white
            )
            Text(text = "Playful with a dash of fresh.",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.white
            )
        }
    }

}


@Composable
private fun Paragraph(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) {

    Column(modifier = modifier) {
        Text(
            text = title,
            color = colorScheme.primaryLightest,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = text,
            color = colorScheme.white,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (MaterialTheme.typography.bodyLarge.fontSize.value + 4f).sp)
        )
    }
}




@Preview()
@Composable
private fun LabSaveScreenPreview() {
    NINUTheme {
        PerfumeMainScreen(
            state = PerfumeMainViewModel.initializeState(),
            action = {},
            navBack = { /*TODO*/ },
            navToLab = {}
        )
    }
}



