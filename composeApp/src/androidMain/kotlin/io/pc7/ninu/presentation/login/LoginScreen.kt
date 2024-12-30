package io.pc7.ninu.presentation.login

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.activities.LoginActivity
import io.pc7.ninu.presentation.activities.RegistrationActivity
import io.pc7.ninu.presentation.components.SocialMediaFooter
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.resource.Display
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.components.util.rememberKeyboardVisibility
import io.pc7.ninu.presentation.theme.NINUTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    navBack: () -> Unit,
    navNext: () -> Unit,
    navResetPassword: (String) -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModelAndroid>().viewModel
) {

    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            LoginEvent.LoginSuccessful -> navNext()
        }
    }


    LoginScreen(
        state = viewModel.state.collectAsState().value,
        action = { viewModel.action(it) },
        navBack = navBack,
        resetPassword = navResetPassword
    )
}


@Composable
private fun LoginScreen(
    state: LoginState,
    action: (LoginAction) -> Unit,
    navBack: () -> Unit,
    resetPassword: (String) -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ButtonTopLeftBack(
            onClick = navBack,
            text = stringResource(R.string.login),
            modifier = Modifier
                .align(Alignment.Start)
        )

        val focusManager = LocalFocusManager.current

        ScrollableColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            NINUTextField(
                value = state.email,
                onUpdate = { action(LoginAction.OnEmailUpdate(it)) },
                placeholderText = stringResource(R.string.email),
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
                onUnfocus = {action(LoginAction.OnEmailRemoveFocus)}

            )

            var passwordVisible by remember { mutableStateOf(false) }
            NINUTextField(
                value = state.password,
                onUpdate = { action(LoginAction.OnPasswordUpdate(it)) },
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
                    onDone = {
                        action(LoginAction.OnLogin)
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
                onUnfocus = {action(LoginAction.OnPasswordRemoveFocus)}
            )
            state.loginState?.let {
                Box(modifier = Modifier
                    .clickable(onClick = {
                        resetPassword(state.email.value)
                    })
                ){
                    it.Display {

                    }
                }
            }



            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        action(LoginAction.OnRememberChange)
                    }
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(30.dp)
                        .then(
                            if (state.rememberMe) {
                                Modifier
                                    .background(colorScheme.primaryLight)
                            } else {
                                Modifier
                                    .border(
                                        1.dp,
                                        color = colorScheme.primaryLight,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(Color.Transparent)
                            }
                        )


                ) {
                    if(state.rememberMe){
                        Icon(painter = painterResource(id = R.drawable.icon_check_bold), contentDescription = null,
                            tint = colorScheme.white,
                            modifier = Modifier
                                .padding(7.dp)
                                .fillMaxSize()
                        )
                    }

                }

                Text(
                    text = stringResource(R.string.remember_me),
                    style = MaterialTheme.typography.labelMedium,
                    color = colorScheme.white
                )


            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        action(LoginAction.OnFailLoginChange)
                    }
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(30.dp)
                        .then(
                            if (state.failLogin) {
                                Modifier
                                    .background(colorScheme.primaryLight)
                            } else {
                                Modifier
                                    .border(
                                        1.dp,
                                        color = colorScheme.primaryLight,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(Color.Transparent)
                            }
                        )


                ) {
                    if(state.failLogin){
                        Icon(painter = painterResource(id = R.drawable.icon_check_bold), contentDescription = null,
                            tint = colorScheme.white,
                            modifier = Modifier
                                .padding(7.dp)
                                .fillMaxSize()
                        )
                    }
                }

                Text(
                    text = "Fail login",
                    style = MaterialTheme.typography.labelMedium,
                    color = colorScheme.white
                )


            }
        }

        Spacer(modifier = Modifier.height(1.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DefaultButtonText(  //login button
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    action(LoginAction.OnLogin)
                },
                text = stringResource(R.string.login),
                isEnabled = state.email.errors.isEmpty()
                        && state.password.errors.isEmpty()
            )

            val keyboardState by rememberKeyboardVisibility()

            val context = LocalContext.current
            if(!keyboardState){
                SocialMediaFooter(
                    onLoginRegisterClick = {
                        val loginActivity = context as LoginActivity
                        val intent = Intent(context, RegistrationActivity::class.java)
                        context.startActivity(intent)
                        loginActivity.finish()
                    },
                    loginPage = true
                )
            }
        }




    }
    
}

@Preview
@Composable
private fun LoginScreenPreview() {
    NINUTheme {
        LoginScreen(
            state = LoginState(),
            action = {},
            navBack = {},
            resetPassword = {}
        )
    }
}
