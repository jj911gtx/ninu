package io.pc7.ninu.presentation.settings.changePasword


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.input.text.NINUPasswordTextInput
import io.pc7.ninu.presentation.settings.changePassword.ChangePasswordAction
import io.pc7.ninu.presentation.settings.changePassword.ChangePasswordState
import io.pc7.ninu.presentation.settings.changePassword.ChangePasswordViewModel
import io.pc7.ninu.presentation.theme.NINUTheme

@Composable
fun ChangePasswordScreen(
    navBack: () -> Unit,
    viewModel: ChangePasswordViewModel,

){

    ChangePasswordScreen(
        state = viewModel.state.collectAsState().value,
        action = { viewModel.action(it) },
        navBack = navBack
    )
    
}

@Composable
private fun ChangePasswordScreen(
    state: ChangePasswordState,
    action: (ChangePasswordAction) -> Unit,
    navBack: () -> Unit
){

    val focusManager = LocalFocusManager.current

    val keyboardActions = remember { KeyboardActions(
        onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        },
        onDone = {action(ChangePasswordAction.ConfirmChange)}
    )}

    Column {
        ButtonTopLeftBack(onClick = navBack, text = "Change password")
        ScrollableColumn(
            modifier = Modifier
                .weight(1f)
        ) {

            Spacer(modifier = Modifier.weight(1f))
            NINUPasswordTextInput(
                password = state.currentPassword,
                onUpdate = {action(ChangePasswordAction.OnCurrentPasswordUpdate(it))},
                placeholder = "Password",
                onUnfocus = {action(ChangePasswordAction.OnCurrentPasswordUnfocus)},
                keyboardActions = keyboardActions,
                imeAction = ImeAction.Next
            )
            NINUPasswordTextInput(
                password = state.newPassword,
                onUpdate = {action(ChangePasswordAction.OnNewPasswordUpdate(it))},
                placeholder = "Confirm password",
                onUnfocus = {action(ChangePasswordAction.OnNewPasswordUnfocus)},
                keyboardActions = keyboardActions,
                imeAction = ImeAction.Next
            )
            NINUPasswordTextInput(
                password = state.confirmNewPassword,
                onUpdate = {action(ChangePasswordAction.OnConfirmNewPasswordUpdate(it))},
                placeholder = "Retype new password",
                onUnfocus = {action(ChangePasswordAction.OnConfirmNewPasswordUnfocus)},
                keyboardActions = keyboardActions,
                imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.weight(10f))
        }
        DefaultButtonText(
            onClick = { action(ChangePasswordAction.ConfirmChange) },
            text = "Change",
            isEnabled = state.currentPassword.errors.isEmpty()
                    && state.newPassword.errors.isEmpty()
                    && state.confirmNewPassword.errors.isEmpty(),

            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}


@Preview
@Composable
private fun ChangePasswordScreenPreview(){
    NINUTheme{
        ChangePasswordScreen(
            state = ChangePasswordState(),
            action = {},
            navBack = {}
        )
    
    }
}



