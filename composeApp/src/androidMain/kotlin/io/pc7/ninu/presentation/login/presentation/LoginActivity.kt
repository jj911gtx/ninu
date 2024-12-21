package ninu.other.loginRegister.login.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import core.presentation.components.util.BackgroundHandling
import core.presentation.theme.NINUTheme
import core.presentation.theme.SCREEN_PADDING


class LoginActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            BackgroundHandling()
            NINUTheme{
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(modifier = Modifier
                        .padding(SCREEN_PADDING)
                    ){
                        LoginNavigation(
                            navBack = {
                                finish()
                            }
                        )
                    }
                }

            }

        }
    }

}