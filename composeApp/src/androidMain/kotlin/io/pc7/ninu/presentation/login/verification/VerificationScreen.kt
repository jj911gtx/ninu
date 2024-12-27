package io.pc7.ninu.presentation.login.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.presentation.components.LoadingDialog
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.resource.Display
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.login.VerificationAction
import io.pc7.ninu.presentation.login.VerificationEvent
import io.pc7.ninu.presentation.login.VerificationState
import io.pc7.ninu.presentation.login.VerificationViewModel
import io.pc7.ninu.presentation.theme.NINUTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun VerificationScreen(
    navNext: () -> Unit,
    navBack: () -> Unit,
    viewModel: VerificationViewModel = koinViewModel<VerificationViewModelAndroid>().viewModel
) {

    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event){
            VerificationEvent.VerificationSuccessful -> navNext()
        }
    }

    VerificationScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack
    )
    
}



@Composable
private fun VerificationScreen(
    state: VerificationState,
    action: (VerificationAction) -> Unit,
    navBack: () -> Unit,
) {



    ScrollableColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        ButtonTopLeftBack(
            onClick = navBack, text = "Verify",
            modifier = Modifier
                .align(Alignment.Start)

        )

        Spacer(modifier = Modifier.weight(1f))


        Text(
            text = "Code has been send to\n${state.email}",
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.white,
            textAlign = TextAlign.Center,
        )
        Column {
            InputBracket(
                code = state.code,
                onValueChange = {action(VerificationAction.OnCodeUpdate(it))},
                checkCode = state.checkCode
            )
            state.checkCode?.Display() {

            }
        }

        when(state.resendCodeRespond){
            Resource.Loading -> {
                LoadingDialog()
            }
            else -> {}
        }
        if(state.timeOut != null){
            Text(text = "Resend code in ${state.timeOut} s",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                color = colorScheme.primaryLight
            )
        }else{

            Text(text = "Resend code",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                color = colorScheme.white,
                modifier = Modifier
                    .clickable{
                        action(VerificationAction.ResendCode)
                    }
            )
        }

        Spacer(modifier = Modifier.weight(1.5f))


        DefaultButtonText(
            onClick = { action(VerificationAction.Verify) },
            text = "Verify",
            isEnabled = state.code.length == 4
        )




    }







}



@Composable
fun InputBracket(
    code: String,
    onValueChange: (String) -> Unit,
    checkCode: Resource<ResultMy<Boolean, DataError.Network>>?,
) {

    BasicTextField(
        value = code,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            repeat(4){ index ->
                val number = when{
                    index >= code.length -> null
                    else -> code[index]
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp,
                            color =
                            if (checkCode is Resource.Result && checkCode.data is ResultMy.Error) {
                                colorScheme.errorMedium
                            } else {
                                if (number != null) colorScheme.white else colorScheme.primaryDark
                            },
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(if (number != null) colorScheme.white.copy(alpha = 0.08f) else Color.Transparent)
                        .size(width = 70.dp, height = 50.dp)
                ) {

                    number?.let{
                        Text(
                            text = number.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = colorScheme.white
                        )
                    }



                }

            }
        }
    }



    
}



@Preview
@Composable
private fun VerificationScreenPreview() {
    NINUTheme {
        VerificationScreen(
            state = VerificationState(
                email = "niko.nako@gmail.com",
                code = "12"),
            action = {},
            navBack = {}
        )

    }
    
}
