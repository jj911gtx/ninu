package io.pc7.ninu.presentation.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.util.BackgroundHandling
import io.pc7.ninu.presentation.main.navigation.BottomNavigationBar
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.main.navigation.SideNavigationBar
import io.pc7.ninu.presentation.theme.SCREEN_PADDING
import io.pc7.ninu.presentation.main.navigation.MainNavigation
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
//    override fun attachBaseContext(newBase: Context) {
//        val language = getLanguage(newBase)
//        super.attachBaseContext(setLocale(newBase, language))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        containerColor = colorScheme.black,
                    ) { padding ->

                        Box(modifier = Modifier
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
