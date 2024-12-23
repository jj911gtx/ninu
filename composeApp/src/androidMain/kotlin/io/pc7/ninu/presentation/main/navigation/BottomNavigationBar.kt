package io.pc7.ninu.presentation.main.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import io.pc7.ninu.presentation.components.navigation.path


@Composable
fun BottomNavigationBar(
    navController: NavController,
    openCloseSidebar: () -> Unit,
) {


    BottomAppBar(
        containerColor = colorScheme.primary
    ) {
        val context = LocalContext.current

        val path = navController.path()

        if(path.CheckPathHasBottomBar()){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                BarItem(
                    icon = R.drawable.icon_home,
                    name = "Home",
                    isSelected = false,
                    onClick = {}
                )
                BarItem(
                    icon = R.drawable.icon_flask,
                    name = "Lab",
                    isSelected = false,
                    onClick = {
                        navController.navigate(MainNavigationRoutes.Lab)
                    }
                )
                BarItem(
                    icon = R.drawable.icon_cart,
                    name = "Where to",
                    isSelected = false,
                    onClick = {
                        navController.navigate(MainNavigationRoutes.WhereTo)
                    }
                )
                BarItem(
                    icon = R.drawable.icon_user,
                    name = "Feel How",
                    isSelected = false,
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
private fun String?.CheckPathHasBottomBar(

): Boolean {

    val screensWithoutBottomBar = remember {
        setOf<String>(MainNavigationRoutes.Onboarding.toString())
    }


    return this?.let { path ->
        screensWithoutBottomBar.none { path.contains(it) }
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