package io.pc7.ninu.presentation.perfumeMainScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.perfumeMain.PerfumeMainAction
import io.pc7.ninu.presentation.perfumeMain.PerfumeMainEvent
import io.pc7.ninu.presentation.perfumeMain.PerfumeMainState
import io.pc7.ninu.presentation.perfumeMain.PerfumeMainViewModel
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


    PerfumeDetailsGeneralScreen(
        navBack = navBack,
        icon = R.drawable.icon_ball,
        number = 2,
        favouriteState = state.isFavourite,
        onFavouriteChage = { action(PerfumeMainAction.OnClickFavourite) },
        onEditMix = navToLab,
        onDelete = { action(PerfumeMainAction.OnClickDelete) },
        buttonEnabled = state.name.value.isNotEmpty(),
        buttonText = "Load scent",
        onButtonClick = {
            action(PerfumeMainAction.OnClickSave)
        },

    ){
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
                        onClick = {action(PerfumeMainAction.OnSelectIcon(it))},
                        isSelected = state.selectedIcon == it
                    )
                }
            }
        }

        Paragraph(
            title = "Great for",
            text = "spring coffee in the city, hot summer days and drinks after work"
        )
        Paragraph(
            title = "Makes you feel",
            text = "joyfull, energised, outgoing, relaxed"
        )
    }
}

@Composable
private fun UnderCardRow(
    modifier: Modifier = Modifier,
    icon: Int,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = null,
            tint = colorScheme.secondaryDark2,
            modifier = Modifier
                .size(18.dp)
        )
        Text(text = text,
            color = colorScheme.secondaryDark2,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = (MaterialTheme.typography.labelMedium.fontSize.value + 4f).sp)
        )
    }
    HorizontalDivider(color = colorScheme.custom3D3D3D)
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


@Preview(widthDp = 200)
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



