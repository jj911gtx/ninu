package io.pc7.ninu.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.pc7.ninu.presentation.components.util.BackgroundHandling
import io.pc7.ninu.presentation.register.RegistrationNavigation
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.SCREEN_PADDING

class RegistrationActivity: ComponentActivity() {


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
                        RegistrationNavigation(
                            navBack = {finish()}
                        )
                    }



                }

            }

        }
    }

}