package io.pc7.ninu.presentation.ai

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.pc7.ninu.presentation.ai.screens.AiChatScreen
import io.pc7.ninu.presentation.ai.screens.AiHomeScreen
import io.pc7.ninu.presentation.ai.screens.AiSurpriseMeScreen
import io.pc7.ninu.presentation.main.navigation.MainNavigationRoutes
import kotlinx.serialization.Serializable


object AiNavigationRoutes{

    @Serializable
    data object AiChat

    @Serializable
    data object SurpriseMe
}

fun NavGraphBuilder.aiNavigation(navController: NavHostController) {
    composable<MainNavigationRoutes.Ai>{
        AiHomeScreen(
            navToChat = {navController.navigate(AiNavigationRoutes.AiChat)},
            navToSurprise = {navController.navigate(AiNavigationRoutes.SurpriseMe)},
        )
    }

    composable<AiNavigationRoutes.AiChat>{
        AiChatScreen(
            navBack = {navController.navigateUp()}
        )
    }

    composable<AiNavigationRoutes.SurpriseMe>{
        AiSurpriseMeScreen(
            navBack = {navController.navigateUp()},
            navHome = {navController.navigate(MainNavigationRoutes.HomeScreen){
                popUpTo<MainNavigationRoutes.HomeScreen>()
            } }
        )
    }
}


