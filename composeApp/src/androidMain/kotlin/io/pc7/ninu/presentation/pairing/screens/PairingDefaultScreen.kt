package io.pc7.ninu.presentation.pairing.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.components.GrayBracketWithText
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonNeedHelp
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.util.rememberKeyboardVisibility


@Composable
fun PairingDefaultScreen(
    backText: String,
    navBack: () -> Unit,
    bracketContentWithTxt: (@Composable () -> Unit)? = null,
    bracketText: String,
    baseBracketContent: (@Composable () -> Unit)? = null,
    onBracketCLick: () -> Unit,

    onClickHelp: () -> Unit,
    buttonOnCLick: () -> Unit,
    buttonText: String,
    isButtonEnabled: Boolean,

    content: @Composable () -> Unit,

) {


    ScrollableColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ButtonTopLeftBack(
            modifier = Modifier.align(Alignment.Start),
            onClick = navBack, text = backText
        )

        if(!rememberKeyboardVisibility().value){
            if(baseBracketContent != null){
                baseBracketContent()

            }else{
                bracketContentWithTxt?.let {
                    GrayBracketWithText(
                        content = bracketContentWithTxt,
                        text = bracketText
                    )
                }
            }

        }



        content()


        Spacer(modifier = Modifier.weight(1f))
        ButtonNeedHelp(onClick = onClickHelp)
        DefaultButtonText(
            onClick = buttonOnCLick,
            text = buttonText,
            isEnabled = isButtonEnabled
        )


    }

}