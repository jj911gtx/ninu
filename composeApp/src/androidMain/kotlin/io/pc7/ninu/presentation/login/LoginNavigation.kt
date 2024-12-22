package io.pc7.ninu.presentation.login

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.pc7.ninu.presentation.activities.LoginActivity
import io.pc7.ninu.presentation.activities.MainActivity
import io.pc7.ninu.presentation.login.newPassword.NewPasswordScreen
import io.pc7.ninu.presentation.login.verification.VerificationScreen
import io.pc7.ninu.presentation.login.verification.VerificationViewModelAndroid
import io.pc7.ninu.presentation.other.SuccessScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun LoginNavigation(
    navBack: () -> Unit,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginNavRoutes.Login
    ) {


        composable<LoginNavRoutes.Login>(){
            val context = LocalContext.current
            LoginScreen(
                navBack = navBack,
                navNext = {
                    val loginActivity = context as LoginActivity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    loginActivity.finish()
                },
                navResetPassword = {email -> navController.navigate(LoginNavRoutes.Verify(email))}

            )
        }


        composable<LoginNavRoutes.Verify>(){
            val email = it.toRoute<LoginNavRoutes.Verify>().email
            VerificationScreen(
                navNext = { navController.navigate(LoginNavRoutes.NewPassword) },
                navBack = {navController.navigateUp()},
                viewModel = koinViewModel<VerificationViewModelAndroid>(parameters = { parametersOf(email) }).viewModel
            )
        }

        composable<LoginNavRoutes.NewPassword>(){
            NewPasswordScreen(
                navBack = {navController.navigateUp()},
                navNext = {navController.navigate(LoginNavRoutes.PasswordChangeSuccess)}
            )
        }


        composable<LoginNavRoutes.PasswordChangeSuccess>(){
            SuccessScreen(
                navNext = {}, //TODO navigate to login screen

            )
        }


    }
}

object LoginNavRoutes{
    @Serializable
    data class Verify(val email: String)
    @Serializable
    data object NewPassword
    @Serializable
    data object Login
    @Serializable
    data object PasswordChangeSuccess
}

