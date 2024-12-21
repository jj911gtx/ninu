package ninu.other.loginRegister.login.presentation.login

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.components.main.ScrollableColumn
import core.presentation.components.main.buttons.DefaultButtonText
import core.presentation.components.main.input.text.NINUTextField
import core.presentation.components.util.rememberKeyboardVisibility
import core.presentation.theme.NINUTheme
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import core.presentation.components.main.buttons.ButtonTopLeftBack
import ninu.login.presentation.login.LoginViewModelAndroid
import ninu.other.loginRegister.login.presentation.LoginActivity
import ninu.other.loginRegister.register.presentation.RegistrationActivity
import ninu.register.presentation.components.SocialMediaFooter
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    navBack: () -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModelAndroid>().viewModel
) {


    LoginScreen(
        state = viewModel.state.collectAsState().value,
        action = { viewModel.action(it) },
        navBack = navBack
    )


}


@Composable
private fun LoginScreen(
    state: LoginState,
    action: (LoginAction) -> Unit,
    navBack: () -> Unit,

) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier
            .fillMaxSize(),
    ) {
        ButtonTopLeftBack(
            onClick = navBack,
            text = "Create your account",
            modifier = Modifier
                .align(Alignment.Start)
        )

        val focusManager = LocalFocusManager.current

        Spacer(modifier = Modifier.weight(1f))
        ScrollableColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            NINUTextField(
                value = state.email,
                onUpdate = { action(LoginAction.OnEmailUpdate(it)) },
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
                onUnfocus = {action(LoginAction.OnEmailRemoveFocus)}

            )

            var passwordVisible by remember { mutableStateOf(false) }
            NINUTextField(
                value = state.password,
                onUpdate = { action(LoginAction.OnPasswordUpdate(it)) },
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
                    text = "Remember me",
                    style = MaterialTheme.typography.labelMedium,
                    color = colorScheme.white
                )


            }
        }


        Spacer(modifier = Modifier.weight(2f))

        DefaultButtonText(  //login button
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                action(LoginAction.OnLogin)
            },
            text = "Log in",
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

@Preview
@Composable
private fun LoginScreenPreview() {
    NINUTheme {
        LoginScreen(
            state = LoginState(),
            action = {},
            navBack = {}
        )
    }
}
