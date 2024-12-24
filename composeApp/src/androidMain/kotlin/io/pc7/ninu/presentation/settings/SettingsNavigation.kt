package io.pc7.ninu.presentation.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.pc7.ninu.presentation.settings.changePasword.ChangePasswordScreen
import io.pc7.ninu.presentation.settings.changePasword.ChangePasswordViewModelAndroid
import io.pc7.ninu.presentation.settings.follow.FollowScreen
import io.pc7.ninu.presentation.settings.language.LanguageSelectionScreen
import io.pc7.ninu.presentation.settings.profile.ProfileScreen
import io.pc7.ninu.presentation.settings.profile.ProfileViewModelAndroid
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.settingsNavigation(navController: NavHostController) {
    composable<SettingsNavigationRoutes.Main> {
        SettingsListScreen(
            navController = navController,
            navBack = {navController.navigateUp()}
        )
    }
    composable<SettingsNavigationRoutes.AboutMe> {
        ProfileScreen(
            viewModel = koinViewModel<ProfileViewModelAndroid>().viewModel,
            navBack = { navController.navigateUp() }
        )
    }
    composable<SettingsNavigationRoutes.FollowNINU> {
        FollowScreen(
            navBack = { navController.navigateUp() }
        )
    }
    composable<SettingsNavigationRoutes.FollowNINU> {
        FollowScreen(
            navBack = { navController.navigateUp() }
        )
    }
    composable<SettingsNavigationRoutes.Language> {
        LanguageSelectionScreen(
            navBack = { navController.navigateUp() }
        )
    }

    composable<SettingsNavigationRoutes.ChangePassword> {
        ChangePasswordScreen(
            navBack = { navController.navigateUp() },
            viewModel = koinViewModel<ChangePasswordViewModelAndroid>().viewModel,
        )
    }



}
