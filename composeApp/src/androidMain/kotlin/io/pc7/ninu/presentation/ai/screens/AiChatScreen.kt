package io.pc7.ninu.presentation.ai.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.domain.model.ai.ChatConversation
import io.pc7.ninu.presentation.ai.AiChatAction
import io.pc7.ninu.presentation.ai.AiChatState
import io.pc7.ninu.presentation.ai.AiChatViewModel
import io.pc7.ninu.presentation.ai.components.Display
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.util.LaunchDisposeEffect
import io.pc7.ninu.presentation.components.util.rememberKeyboardVisibility
import io.pc7.ninu.presentation.components.util.setBottomBar
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.custom.getPrimaryGradient
import org.koin.androidx.compose.koinViewModel


@Composable
fun AiChatScreen(
    navBack: () -> Unit,
    viewModel: AiChatViewModel = koinViewModel<AiChatViewModelAndroid>().viewModel
) {

    val activity = LocalContext.current as Activity
    LaunchDisposeEffect(
        launched = { activity.setBottomBar(true) },
        disposed = {activity.setBottomBar(false)}
    )

    AiChatScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack
    )

}

@Composable
private fun AiChatScreen(
    navBack: () -> Unit,
    state: AiChatState,
    action: (AiChatAction) -> Unit
) {

    val keyboardVisible = rememberKeyboardVisibility().value

    Column(


    ) {
        ButtonTopLeftBack(onClick = navBack, text = "Ask AI for Advice")
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {

            if(state.chatHistory.isEmpty()){
                if( !keyboardVisible) {
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {

                        Image(painter = painterResource(id = R.drawable.icon_ai), contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                        Text(
                            text = "Your Scent Stylist Awaits!",
                            style = MaterialTheme.typography.headlineMedium,
                            color = colorScheme.white,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "Ask our AI for tailored fragrance advice",
                            style = MaterialTheme.typography.titleLarge,
                            color = colorScheme.white,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(1f))
                if( !keyboardVisible){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(text = "Try out examples:",
                            style = MaterialTheme.typography.headlineMedium,
                            color = colorScheme.white,
                            textAlign = TextAlign.Center,
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            List(3){"What scent should I wear for a romantic dinner?"}.forEach {
                                ExampleBracket(it)
                            }
                        }
                    }
                }
            }else{
                state.chatHistory.Display(modifier = Modifier.weight(1f))
            }






            Spacer(modifier = Modifier.height(5.dp))


            NINUTextField(
                value = state.text,
                onUpdate = {
                    action(AiChatAction.OnTextUpdate(it))
                },
                placeholderText = "",
                suffix = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(brush = getPrimaryGradient())
                            .size(30.dp)
                            .clickable {
                                action(AiChatAction.OnSendText)
                            }

                    ) {
                        Icon(painter = painterResource(id = R.drawable.icon_send), contentDescription = null,
                            tint = colorScheme.white,
                            modifier = Modifier
                                .padding(end = 2.dp)
                        )
                    }
                },
                borderOkColor = colorScheme.white.copy(alpha = 0.2f),
                borderEmptyColor = colorScheme.white.copy(alpha = 0.2f),
                focusedContainerColor = colorScheme.white.copy(alpha = 0.1f),
                unfocusedContainerColor = colorScheme.white.copy(alpha = 0.1f),

            )
        }
        

    }

}

@Composable
fun  List<ChatConversation>.Display(
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    LaunchedEffect(this) {
        scrollState.animateScrollTo(scrollState.maxValue)
        focusManager.clearFocus()
    }
    ScrollableColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        inScrollState = scrollState,
        modifier = modifier
            .fillMaxWidth()

    ) {
        this@Display.forEach {
            it.Display(columnScope = this)
        }
    }
}




@Composable
fun ExampleBracket(
    text: String,

) {

    CardBracket(onClick = { /*TODO*/ },
        cornerShape = 10.dp,
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }

    
}


@Preview
@Composable
private fun AiChatScreenPreview() {
    NINUTheme {
        AiChatScreen(
            state = AiChatState(chatHistory = listOf(
                ChatConversation.MyText("What scent should I wear for a romantic dinner?"),
                ChatConversation.AiText("What scent should I wear for a romantic dinner?"),
                ChatConversation.MyText("What scent should I wear for a romantic dinner?"),
                ChatConversation.AiText("What" +
                        ""),
            )),
            action = {},
            navBack = {}
        )    
    }
    
}