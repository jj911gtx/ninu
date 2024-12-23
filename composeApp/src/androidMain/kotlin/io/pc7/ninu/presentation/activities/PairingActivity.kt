package io.pc7.ninu.presentation.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.components.util.BackgroundHandling
import io.pc7.ninu.presentation.pairing.PairingNavigation
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.SCREEN_PADDING
import io.pc7.ninu.presentation.util.permission.ManageBluetoothPermissionDisplay
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.core.scope.Scope

class PairingActivity: ComponentActivity(), AndroidScopeComponent {
    override val scope: Scope by activityRetainedScope()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BackgroundHandling()


            NINUTheme{
                Scaffold {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        Box(modifier = Modifier
                            .padding(SCREEN_PADDING)
                            .padding(10.dp)
                        ){
                            PairingNavigation(
                                koinScope = scope,
                                navBack = { finish() }
                            )
                        }
                    }
                }
            }
        }
    }
}