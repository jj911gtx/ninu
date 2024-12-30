package io.pc7.ninu.presentation.ai.screens

import android.app.Activity
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.ai.AiSurpriseMeAction
import io.pc7.ninu.presentation.ai.AiSurpriseMeState
import io.pc7.ninu.presentation.ai.AiSurpriseMeViewModel
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.util.LaunchDisposeEffect
import io.pc7.ninu.presentation.components.util.setBottomBar
import io.pc7.ninu.presentation.perfumeDetailsGeneral.GreatForMakesYouFeelContent
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.custom.colorScheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun AiSurpriseMeScreen(
    navBack: () -> Unit,
    navHome: () -> Unit,
    viewModel: AiSurpriseMeViewModel = koinViewModel<AiSurpriseMeViewModelAndroid>().viewModel
) {
    val activity = LocalContext.current as Activity
    LaunchDisposeEffect(
        launched = { activity.setBottomBar(true) },
        disposed = {activity.setBottomBar(false)}
    )

    AiSurpriseMeScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack,
        navHome = navHome
    )

}

@Composable
private fun AiSurpriseMeScreen(
    state: AiSurpriseMeState,
    action: (AiSurpriseMeAction) -> Unit,
    navBack: () -> Unit,
    navHome: () -> Unit
) {
    Column {
        ButtonTopLeftBack(onClick = navBack, text = "Get AI Surprise")

        ScrollableColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Image(painter = painterResource(id = R.drawable.icon_ai), contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )

            Text(text = "NINU AI has a surprise for you!",
                style = MaterialTheme.typography.headlineSmall,
                color = colorScheme.white,
                textAlign = TextAlign.Center,
            )

            Text(text = "AI crafted a unique blend crafted just for your style",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.white,
                textAlign = TextAlign.Center,
            )

            CardBracket(onClick = {  },
                cornerShape = 20.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Row {
                        Icon(painter = painterResource(id = R.drawable.icon_fingerprint), contentDescription = null)

                    }

                    Text(
                        text = "Authentic Touch",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                        color = colorScheme.white,
                    )
                    Text(
                        text = "100% Work day fragrance.",
                        style = MaterialTheme.typography.titleLarge,
                        color = colorScheme.white,
                    )

                    GreatForMakesYouFeelContent()
                }
                
            }



        }

        DefaultButtonText(
            onClick = navHome,
            text = stringResource(R.string.load_scent),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }


}


@Preview
@Composable
private fun AiSurpriseMeScreenPreview() {
    NINUTheme {
        AiSurpriseMeScreen(
            state = AiSurpriseMeState(),
            action = {},
            navBack = {},
            navHome = {}
        )
    }

}