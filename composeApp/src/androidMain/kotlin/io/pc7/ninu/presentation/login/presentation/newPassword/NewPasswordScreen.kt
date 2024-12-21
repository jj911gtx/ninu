package ninu.login.presentation.newPassword

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import core.presentation.components.main.ScrollableColumn
import core.presentation.components.main.buttons.DefaultButtonText
import core.presentation.components.main.input.text.NINUTextField
import core.presentation.theme.NINUTheme
import io.pc7.ninu.R
import core.presentation.components.main.buttons.ButtonTopLeftBack
import org.koin.androidx.compose.koinViewModel


@Composable
fun NewPasswordScreen(
    viewModel: NewPasswordViewModel = koinViewModel<NewPasswordViewModelAndroid>().viewModel
) {


    NewPasswordScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)}
    )



}


@Composable
private fun NewPasswordScreen(
    state: NewPasswordState,
    action: (NewPasswordAction) -> Unit,
) {
    Column {
        ButtonTopLeftBack(
            onClick = { /*TODO*/ }, text = "New password",
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
                placeholderText = "Password",
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
                placeholderText = "Confirm password",
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
                text = "Confirm",
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
            action = {}
        )

    }
}
