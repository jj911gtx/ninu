package io.pc7.ninu.presentation.perfumeSelection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.other.CircleItemSelect
import io.pc7.ninu.presentation.theme.NINUTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun PremadeOptionScreen(
    navWhereTo: () -> Unit,
    navFeelHow: () -> Unit,
) {

    var selected by remember { mutableStateOf<Int?>(null) }

    val coroutine = rememberCoroutineScope()
    fun launch(onTimeout: () -> Unit) {
        coroutine.launch {
            delay(700)
            onTimeout()
            withContext(Dispatchers.IO){
                delay(500)
                selected = null
            }


        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(painter = painterResource(id = R.drawable.textlogo), contentDescription = null,
            modifier = Modifier.size(150.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircleItemSelect(
                selected = selected == 1,
                onClick = {
                    selected = 1
                    launch(navWhereTo)
                },
                iconId = R.drawable.icon_cart,
                name = stringResource(R.string.where_to)
            )
            CircleItemSelect(
                selected = selected == 2,
                onClick = {
                    selected = 2
                    launch(navFeelHow)
                },
                iconId = R.drawable.icon_user,
                name = stringResource(R.string.feel_how)
            )
        }


        Spacer(modifier = Modifier.weight(2f))

    }



}

@Preview
@Composable
private fun PremadeOptionScreenPreview() {
    NINUTheme {
        PremadeOptionScreen(navWhereTo = { -> }, navFeelHow = { -> }

        )

    }
}