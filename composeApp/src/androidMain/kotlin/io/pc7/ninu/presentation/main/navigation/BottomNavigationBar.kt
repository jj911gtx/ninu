package io.pc7.ninu.presentation.main.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import androidx.navigation.NavController
import io.pc7.ninu.presentation.components.navigation.isRoute
import io.pc7.ninu.presentation.components.navigation.path
import io.pc7.ninu.presentation.components.util.setBottomBar


@Composable
fun BottomNavigationBar(
    navController: NavController,
    openCloseSidebar: () -> Unit,
) {
    val path = navController.path()

    val activity = LocalContext.current as Activity

    if(path.checkPathHasBottomBar()){
        LaunchedEffect(Unit) {
            activity.setBottomBar(false)
        }
        DisposableEffect(Unit) {
            onDispose {
                activity.setBottomBar(true)
            }
        }

        BottomAppBar(
            containerColor = colorScheme.primary,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                BarItem(
                    icon = R.drawable.icon_home,
                    name = "Home",
                    isSelected = path.isRoute(MainNavigationRoutes.HomeScreen),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.HomeScreen){
                            popUpTo<MainNavigationRoutes.HomeScreen>()
                        }
                    }
                )
                BarItem(
                    icon = R.drawable.icon_flask,
                    name = "Lab",
                    isSelected = path.isRoute(MainNavigationRoutes.Lab),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.Lab){
                            popUpTo<MainNavigationRoutes.Lab>()
                        }
                    }
                )
                BarItem(
                    icon = R.drawable.icon_cart,
                    name = "Where to",
                    isSelected = path.isRoute(MainNavigationRoutes.WhereTo),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.WhereTo)
                    }
                )
                BarItem(
                    icon = R.drawable.icon_user,
                    name = "Feel How",
                    isSelected = path.isRoute(MainNavigationRoutes.FeelHow),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.FeelHow)
                    }
                )
                BarItem(
                    icon = R.drawable.icon_bars,
                    name = "More",
                    isSelected = false,
                    onClick = openCloseSidebar
                )

            }
        }
    }
}


@Composable
private fun String?.checkPathHasBottomBar(

): Boolean {

    val screensWithoutBottomBar = remember {
        setOf(
            MainNavigationRoutes.Onboarding,
            MainNavigationRoutes.Lab,
            MainNavigationRoutes.PerfumeSave,
        )
    }


    return this?.let { path ->
        screensWithoutBottomBar.none { path.isRoute(it) }
    } ?: true
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun RowScope.BarItem(
    modifier: Modifier = Modifier,
    icon: Int,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val color = if(isSelected) colorScheme.white else colorScheme.primaryLight

//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.weight(1f)
//    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable(onClick = onClick)
        ) {
            Column(
                modifier = modifier

                    .padding(horizontal = 5.dp, vertical = 5.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = color
                )
                Text(
                    text = name,
                    color = color,
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
//    }

    
}