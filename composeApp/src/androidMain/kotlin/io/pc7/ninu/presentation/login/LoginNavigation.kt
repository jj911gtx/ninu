package io.pc7.ninu.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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

        }

        composable("New password"){

        }


    }



}