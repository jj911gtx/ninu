package io.pc7.ninu.presentation.main.navigation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.activities.LoginActivity
import io.pc7.ninu.presentation.activities.PairingActivity
import io.pc7.ninu.presentation.activities.RegistrationActivity
import io.pc7.ninu.presentation.components.ProfileImage
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.settings.SettingsNavigationRoutes
import io.pc7.ninu.presentation.theme.NINUTheme
import kotlinx.coroutines.launch


@Composable
fun SideNavigationBar(
    navController: NavController,
    drawerState: DrawerState,
) {

    val coroutine = rememberCoroutineScope()

    @Composable
    fun NavBarItemText(
        text: String,
        onClick: () -> Unit,
    ) {
        Text(text = text,
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.secondaryLight2,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable{
                    coroutine.launch {
                        drawerState.close()
                    }
                    onClick()
                }
        )
    }

//    Surface {
        ScrollableColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .background(colorScheme.black)
                .padding(horizontal = 40.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .clickable{
//                        navController.navigate(MainNavigationRoutes.Profile)
                    }
            ) {
                ProfileImage(size = 100.dp)


                Text(text = "John Novak",
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 20.sp),
                    color = colorScheme.white
                )
            }


            Spacer(modifier = Modifier.height(30.dp))



//            language
//            faq
//            contact us
//            follow
//            statistics
//            favourites



            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(30.dp),

            ) {
                val context = LocalContext.current
                NavBarItemText(
                    text = stringResource(R.string.pairing),
                    onClick = {
                        val intent = Intent(context, PairingActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                NavBarItemText(
                    text = stringResource(R.string.login),
                    onClick = {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                NavBarItemText(
                    text = stringResource(R.string.register),
                    onClick = {
                        val intent = Intent(context, RegistrationActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                NavBarItemText(
                    text = stringResource(R.string.settings),
                    onClick = { navController.navigate(SettingsNavigationRoutes.Main) }
                )
                NavBarItemText(
                    text = stringResource(R.string.statistics),
                    onClick = { navController.navigate(MainNavigationRoutes.Statistics) }
                )

//                NavBarItemText(
//                    text = "Settings",
//                    onClick = {
//                        onClickItem()
//                        navController.navigate(MainNavigationRoutes.Language)
//                    }
//                )
//                NavBarItemText(
//                    text = "FAQ, ContactUs",
//                    onClick = {
//                        onClickItem()
//                        navController.navigate(MainNavigationRoutes.FAQ)
//                    }
//                )
//                NavBarItemText(
//                    text = "Follow NINU",
//                    onClick = {
//                        onClickItem()
//                        navController.navigate(MainNavigationRoutes.Follow)
//                    }
//                )
//                NavBarItemText(
//                    text = "Statistics",
//                    onClick = {
//                        onClickItem()
//                        navController.navigate(MainNavigationRoutes.Statistics)
//                    }
//                )

//            ------------------------------------------------------------------------------------------------------------------------------------------------
//
        //
        //
        //
        //
        //
        //
        //
        //                NavBarItemText(
//                    text = "Settings",
//                    onClick = { navController.navigate(NavRoutes.SETTINGS) }
//                )
//                NavBarItemText(
//                    text = "Perfume status",
//                    onClick = { navController.navigate(NavRoutes.PERFUME_STATUS) }
//                )
//
//                NavBarItemText(
//                    text = "How to apply",
//                    onClick = { navController.navigate(NavRoutes.HOW_TO_APPLY) }
//                )
//
//                NavBarItemText(
//                    text = "Usage stats",
//                    onClick = { navController.navigate(NavRoutes.USAGE_STATS) }
//                )
//
//                NavBarItemText(
//                    text = "About My NINU",
//                    onClick = { navController.navigate(NavRoutes.ABOUT_MY_NINU) }
//                )
//
//                NavBarItemText(
//                    text = "Lock NINU",
//                    onClick = { navController.navigate(NavRoutes.LOCK_NINU) }
//                )
//
//                NavBarItemText(
//                    text = "Support",
//                    onClick = { navController.navigate(NavRoutes.SUPPORT) }
//                )
            }
            Spacer(modifier = Modifier.height(30.dp))






//        }
    }
    
}




@Preview
@Composable
private fun SideBarPreview() {
    val navController = rememberNavController()
    NINUTheme {
         SideNavigationBar(navController = navController, rememberDrawerState(initialValue = DrawerValue.Closed))

    }

}