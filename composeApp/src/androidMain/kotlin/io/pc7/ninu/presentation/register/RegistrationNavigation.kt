package io.pc7.ninu.presentation.register

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.pc7.ninu.presentation.activities.LoginActivity
import io.pc7.ninu.presentation.activities.RegistrationActivity
import io.pc7.ninu.presentation.register.registration.RegistrationScreen
import io.pc7.ninu.presentation.register.userInfo.UserInfoInputScreen
import io.pc7.ninu.presentation.other.SuccessScreen



@Composable
fun RegistrationNavigation(
    navBack: () -> Unit,
) {

    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "UserInfo"
    ) {
        composable("Register"){
            RegistrationScreen(
                login = {
                    val registrationActivity = context as RegistrationActivity
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    registrationActivity.finish()
                },
                navNext = { navController.navigate("UserInfo") },
                navBack = navBack
            )
        }
        composable("UserInfo"){
            UserInfoInputScreen(
                next = { navController.navigate("Completed") },
                back = { navController.navigateUp() }
            )
        }

        composable("Completed"){
            SuccessScreen(
                navNext = navBack
            )
        }


    }

}