package io.pc7.ninu.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun NavController.path(): String?{
    val navBackStackEntry by this.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}