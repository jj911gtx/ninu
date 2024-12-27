package io.pc7.ninu.presentation.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import io.pc7.ninu.presentation.components.util.BackgroundHandling
import io.pc7.ninu.presentation.components.util.removeBackgroundImage
import io.pc7.ninu.presentation.components.util.setBackgroundImage
import io.pc7.ninu.presentation.main.navigation.BottomNavigationBar
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.main.navigation.SideNavigationBar
import io.pc7.ninu.presentation.theme.SCREEN_PADDING
import io.pc7.ninu.presentation.main.navigation.MainNavigation
import io.pc7.ninu.presentation.util.getLanguage
import io.pc7.ninu.presentation.util.setLocale
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val language = getLanguage(newBase)
        super.attachBaseContext(setLocale(newBase, language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        removeBackgroundImage()
        setContent {
            BackgroundHandling()
////            ManageBluetoothPermissionDisplay()
            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            NINUTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = { SideNavigationBar(
                        navController,
                        drawerState
                    ) },
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(
                            navController,
                            openCloseSidebar = {
                                coroutineScope.launch {
                                    if(drawerState.isOpen){ drawerState.close() }
                                    else{ drawerState.open() }
                                }
                            }
                        ) },
                        containerColor = Color.Transparent,
                    ) { padding ->

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    0.0f to Color.Black.copy(alpha = 0.6f),
                                    0.48f to Color.Transparent,
                                    1.0f to Color.Black.copy(alpha = 0.6f)
                                )
                            )

                            .padding(padding)
                            .padding(SCREEN_PADDING)
                        ) {
                            MainNavigation(
                                navController
                            )
                        }
                    }
                }
            }

        }
    }
}
