package io.pc7.ninu.presentation.register.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.SocialMediaFooter
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.resource.Display
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.components.util.rememberKeyboardVisibility
import io.pc7.ninu.presentation.register.register.RegistrationAction
import io.pc7.ninu.presentation.register.register.RegistrationEvent
import io.pc7.ninu.presentation.register.register.RegistrationState
import io.pc7.ninu.presentation.register.register.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegistrationScreen(
    navNext: () -> Unit,
    navBack: () -> Unit,
    login: () -> Unit,
    viewModel: RegistrationViewModel = koinViewModel<RegistrationViewModelAndroid>().viewModel
) {

    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            RegistrationEvent.Success -> navNext()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkInputs()
    }

    RegistrationScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        login = login,
        navBack = navBack
    )

}

@Composable
private fun RegistrationScreen(
    login: () -> Unit,
    state: RegistrationState,
    action: (RegistrationAction) -> Unit,
    navBack: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(),
    ) {

        ButtonTopLeftBack(
            onClick = navBack, text = "Create your account",
            modifier = Modifier
                .align(Alignment.Start)
        )
        Spacer(modifier = Modifier.weight(1f))
        ScrollableColumn(
            modifier = Modifier
        ) {



            NINUTextField(
                value = state.name,
                onUpdate = { action(RegistrationAction.OnNameUpdate(it)) },
                placeholderText = "Name",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                imeAction = ImeAction.Next,
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = null
                    )
                },
                onUnfocus = {action(RegistrationAction.OnNameRemoveFocus)},
            )
            NINUTextField(
                value = state.surname,
                onUpdate = { action(RegistrationAction.OnSurnameUpdate(it)) },
                placeholderText = "Surname",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                imeAction = ImeAction.Next,
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = null
                    )
                },
                onUnfocus = {action(RegistrationAction.OnSurnameRemoveFocus)},
            )

            NINUTextField(
                value = state.email,
                onUpdate = { action(RegistrationAction.OnEmailUpdate(it)) },
                placeholderText = "Email",
                keyboardType = KeyboardType.Email,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                imeAction = ImeAction.Next,
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_mail),
                        contentDescription = null
                    )
                },
                onUnfocus = {action(RegistrationAction.OnEmailRemoveFocus)}

            )

            var passwordVisible by remember { mutableStateOf(false) }
            NINUTextField(
                value = state.password,
                onUpdate = { action(RegistrationAction.OnPasswordUpdate(it)) },
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
                    onDone = {
                        action(RegistrationAction.OnSignup)
                    }
                ),
                imeAction = ImeAction.Done,
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
                onUnfocus = {action(RegistrationAction.OnPasswordRemoveFocus)}
            )

        }


        Spacer(modifier = Modifier.weight(2f))

        state.loginRespond?.Display(successDisplay = {})
        DefaultButtonText(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                action(RegistrationAction.OnSignup)
            },
            text = "Sign up",
            isEnabled = state.email.errors.isEmpty()
                    && state.password.errors.isEmpty()
                    && state.name.errors.isEmpty()
                    && state.surname.errors.isEmpty()
        )


        val keyboardState by rememberKeyboardVisibility()
        if(!keyboardState){
            SocialMediaFooter(
                onLoginRegisterClick = login,
                loginPage = false
            )
        }


    }



}




@Preview
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(
        state = RegistrationState(),
        action = {},
        login = {},
        navBack = {}
    )
}

