package io.pc7.ninu.presentation.ai.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.other.CircleItemSelect
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.custom.getPrimaryGradient
import io.pc7.ninu.presentation.theme.montserratFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AiHomeScreen(
    navToChat: () -> Unit,
    navToSurprise: () -> Unit
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.textlogo), contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.icon_ai),
            contentDescription = null,
        )

        Row {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    brush = getPrimaryGradient(
                        tileMode = TileMode.Mirror
                    ),
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    letterSpacing = 15.sp
                )
            )
            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(R.string.ai).uppercase(),
                style = MaterialTheme.typography.headlineLarge.copy(letterSpacing = 15.sp, fontSize = 40.sp),
                color = colorScheme.white
            )
        }


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
                    launch(navToChat)
                },
                iconId = R.drawable.icon_chat,
                name = stringResource(R.string.ai_assistant)
            )
            CircleItemSelect(
                selected = selected == 2,
                onClick = {
                    selected = 2
                    launch(navToSurprise)
                },
                iconId = R.drawable.present,
                name = stringResource(R.string.surprise_me)
            )
        }

        Spacer(modifier = Modifier.weight(3f))
    }



}
fun Size.isInfinite() = width.isInfinite() && height.isInfinite()

fun Modifier.defaultSizeFor(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            DefaultIconSizeModifier
        } else {
            Modifier
        }
    )

val IconSize = 24.0.dp
private val DefaultIconSizeModifier = Modifier.size(IconSize)


@Preview
@Composable
private fun AiHomeScreenPreview() {
    NINUTheme {
        AiHomeScreen(
            navToChat = {},
            navToSurprise = {},

        )
    }

}