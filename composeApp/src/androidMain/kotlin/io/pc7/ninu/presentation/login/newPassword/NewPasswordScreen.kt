package io.pc7.ninu.presentation.login.newPassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.login.NewPasswordAction
import io.pc7.ninu.presentation.login.NewPasswordEvent
import io.pc7.ninu.presentation.login.NewPasswordState
import io.pc7.ninu.presentation.login.NewPasswordViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewPasswordScreen(
    navBack: () -> Unit,
    navNext: () -> Unit,
    viewModel: NewPasswordViewModel = koinViewModel<NewPasswordViewModelAndroid>().viewModel
) {

    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            NewPasswordEvent.ChangeSuccessful -> navNext()
        }
    }

    NewPasswordScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack
    )
}


@Composable
private fun NewPasswordScreen(
    state: NewPasswordState,
    action: (NewPasswordAction) -> Unit,
    navBack: () -> Unit
) {
    Column {
        ButtonTopLeftBack(
            onClick = navBack, text = stringResource(R.string.new_password),
            modifier = Modifier
                .align(Alignment.Start)
        )


        ScrollableColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

        ) {
            Spacer(modifier = Modifier.weight(1f))
            val focusManager = LocalFocusManager.current

            var passwordVisible by remember { mutableStateOf(false) }
            NINUTextField(
                value = state.password,
                onUpdate = { action(NewPasswordAction.OnNewPasswordUpdate(it)) },
                placeholderText = stringResource(R.string.password),
                textHide = !passwordVisible,
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_password),
                        contentDescription = null,
                    )
                },
                keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                imeAction = ImeAction.Next,
                suffix = {
                    if (passwordVisible) {
                        Icon(
                            painterResource(id = R.drawable.icon_visible),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable{passwordVisible = false}
                        )
                    } else {
                        Icon(
                            painterResource(id = R.drawable.icon_hide),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable{passwordVisible = true}
                        )
                    }
                },
                onUnfocus = {action(NewPasswordAction.OnNewPasswordRemoveFocus)}
            )

            var confirmNewPasswordVisible by remember { mutableStateOf(false) }
            NINUTextField(
                value = state.confirmPassword,
                onUpdate = { action(NewPasswordAction.OnConfirmNewPasswordUpdate(it)) },
                placeholderText = stringResource(R.string.confirm_password),
                textHide = !confirmNewPasswordVisible,
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_password),
                        contentDescription = null,
                    )
                },
                keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(
                    onDone = {
                        action(NewPasswordAction.Confirm)
                    }
                ),
                imeAction = ImeAction.Done,
                suffix = {
                    if (passwordVisible) {
                        Icon(
                            painterResource(id = R.drawable.icon_visible),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable{confirmNewPasswordVisible = false}
                        )
                    } else {
                        Icon(
                            painterResource(id = R.drawable.icon_hide),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable{confirmNewPasswordVisible = true}
                        )
                    }
                },
                onUnfocus = {action(NewPasswordAction.OnConfirmNewPasswordRemoveFocus)}
            )
            Spacer(modifier = Modifier.weight(1.5f))

            DefaultButtonText(
                onClick = { action(NewPasswordAction.Confirm) },
                text = stringResource(R.string.confirm),
                isEnabled = state.buttonEnabled
            )
        }



    }





}


@Preview
@Composable
private fun NewPasswordScreenPreview() {
    NINUTheme {
        NewPasswordScreen(
            state = NewPasswordState(),
            action = {},
            navBack = {}
        )

    }
}
