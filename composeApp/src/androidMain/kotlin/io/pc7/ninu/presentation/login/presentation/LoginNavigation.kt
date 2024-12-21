package ninu.other.loginRegister.login.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ninu.other.loginRegister.login.presentation.login.LoginScreen
import ninu.login.presentation.newPassword.NewPasswordScreen
import ninu.login.presentation.verification.VerificationScreen


@Composable
fun LoginNavigation(
    navBack: () -> Unit,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {

        composable(route = "Login"){
            LoginScreen(
                navBack = navBack
            )
        }


        composable("Verify"){
            VerificationScreen(

            )
        }

        composable("New password"){
            NewPasswordScreen(

            )
        }


    }



}