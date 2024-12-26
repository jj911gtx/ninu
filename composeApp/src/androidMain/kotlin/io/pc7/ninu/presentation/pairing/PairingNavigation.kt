package io.pc7.ninu.presentation.pairing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.pc7.ninu.presentation.pairing.screens.purchaseInfo.PurchaseInfoScreen
import feature.pairing.screens.purchaseInfo.PurchaseInfoViewModelAndroid
import io.pc7.ninu.presentation.pairing.screens.scan.ScanScreen
import io.pc7.ninu.presentation.pairing.screens.scan.ScanViewModelAndroid
import org.koin.androidx.compose.koinViewModel
import org.koin.core.scope.Scope


@Composable
fun PairingNavigation(
    koinScope: Scope,
    navBack: () -> Unit,
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "PurchaseInfo"
    ){

        composable("Scan") {
            ScanScreen(
                viewModel = koinViewModel<ScanViewModelAndroid>(scope = koinScope).viewModel,
                navNext = { navController.navigate("PurchaseInfo") },
                navBack = navBack
            )
        }

        composable("PurchaseInfo") {
            PurchaseInfoScreen(
                viewModel = koinViewModel<PurchaseInfoViewModelAndroid>(scope = koinScope).viewModel,
                navBack = {navController.navigateUp()}
            )
        }

    }

}
