package io.pc7.ninu.presentation.main.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import androidx.navigation.NavController
import io.pc7.ninu.presentation.ai.AiNavigationRoutes
import io.pc7.ninu.presentation.components.navigation.isRoute
import io.pc7.ninu.presentation.components.navigation.path
import io.pc7.ninu.presentation.components.util.LaunchDisposeEffect
import io.pc7.ninu.presentation.components.util.setBottomBar


@Composable
fun BottomNavigationBar(
    navController: NavController,
    openCloseSidebar: () -> Unit,
) {
    val path = navController.path()

    val activity = LocalContext.current as Activity



    if(path.checkPathHasBottomBar()){
        LaunchDisposeEffect(
            launched = { activity.setBottomBar(false) },
            disposed = { activity.setBottomBar(true) }
        )
        BottomAppBar(
            containerColor = colorScheme.primary,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                BarItem(
                    icon = R.drawable.icon_home,
                    name = stringResource(R.string.home),
                    isSelected = path.isRoute(MainNavigationRoutes.HomeScreen),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.HomeScreen){
                            popUpTo<MainNavigationRoutes.HomeScreen>()
                        }
                    }
                )
                BarItem(
                    icon = R.drawable.icon_flask,
                    name = stringResource(R.string.lab),
                    isSelected = path.isRoute(MainNavigationRoutes.Lab),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.Lab){
                            popUpTo<MainNavigationRoutes.Lab>()
                        }
                    }
                )
                BarItem(
                    icon = R.drawable.icon_person,
                    name = stringResource(R.string.premade),
                    isSelected = path.isRoute(MainNavigationRoutes.Premade),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.Premade)
                    }
                )
//                BarItem(
//                    icon = R.drawable.icon_user,
//                    name = "Feel How",
//                    isSelected = path.isRoute(MainNavigationRoutes.FeelHow),
//                    onClick = {
//                        navController.navigate(MainNavigationRoutes.FeelHow)
//                    }
//                )
                BarItem(
                    icon = R.drawable.icon_ai_bold,
                    name = stringResource(R.string.ai),
                    isSelected = path.isRoute(MainNavigationRoutes.Ai),
                    onClick = {
                        navController.navigate(MainNavigationRoutes.Ai)
                    }
                )
                BarItem(
                    icon = R.drawable.icon_bars,
                    name = stringResource(R.string.more),
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
            AiNavigationRoutes.AiChat,
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
                Box(modifier = Modifier.size(30.dp)){
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                }
                Text(
                    text = name,
                    color = color,
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
//    }

    
}