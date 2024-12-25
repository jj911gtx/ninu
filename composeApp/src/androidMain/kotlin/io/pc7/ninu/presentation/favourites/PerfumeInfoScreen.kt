package io.pc7.ninu.presentation.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeDetailsGeneralScreen
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PerfumeInfoScreen(
    modifier: Modifier = Modifier,
    navBack: () -> Unit,
    navToLab: () -> Unit,
) {



}



@Composable
private fun PerfumeInfoScreen(
    navBack: () -> Unit,
    navToLab: () -> Unit,
) {

    var favourite by remember { mutableStateOf(true) }

    PerfumeDetailsGeneralScreen(
        navBack = navBack,
        icon = R.drawable.icon_apple,
        favouriteState = favourite,
        onFavouriteChage = { favourite = !favourite },
        onEditMix = navToLab,
        buttonEnabled = true,
        onButtonClick = navBack,
        buttonText = "Load scent"
    ) {

        Text(
            text = "Lemon Lime",
            color = colorScheme.white,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 32.sp),
        )
//        Spacer(modifier = Modifier.height(20.dp))

        Column {
            Text(
                text = "Olfactive family",
                color = colorScheme.white,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "FRUITY, AMBER, SPICY",
                color = colorScheme.white,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
            )
        }

        Text(
            text = "Fragrance ratio",
            color = colorScheme.white, //TODO

        )
    }



}

@Preview
@Composable
private fun Prev() {
    NINUTheme {
        PerfumeInfoScreen(
            navBack = {},
            navToLab = {}
        )
    }
    
}