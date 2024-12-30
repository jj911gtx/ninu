package io.pc7.ninu.presentation.pairing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.pc7.ninu.presentation.main.navigation.MainNavigationRoutes
import io.pc7.ninu.presentation.pairing.screens.purchaseInfo.PurchaseInfoScreen
import io.pc7.ninu.presentation.pairing.screens.purchaseInfo.PurchaseInfoViewModelAndroid
import io.pc7.ninu.presentation.pairing.screens.scan.ScanScreen
import io.pc7.ninu.presentation.pairing.screens.scan.ScanViewModelAndroid
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope


@Composable
fun PairingNavigation(
    koinScope: Scope,
    navBack: () -> Unit,
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PerfumeNavigationRoutes.Scan
    ){

        composable<PerfumeNavigationRoutes.Scan> {
            ScanScreen(
                viewModel = koinViewModel<ScanViewModelAndroid>(scope = koinScope).viewModel,
                navNext = { navController.navigate(PerfumeNavigationRoutes.PurchaseInfo(deviceMac = it)) },
                navBack = navBack
            )
        }

        composable<PerfumeNavigationRoutes.PurchaseInfo>() {
            val deviceMac: String = it.toRoute<PerfumeNavigationRoutes.PurchaseInfo>().deviceMac
            PurchaseInfoScreen(
                viewModel = koinViewModel<PurchaseInfoViewModelAndroid>(scope = koinScope, parameters = { parametersOf(deviceMac) }).viewModel,
                navBack = {navController.navigateUp()},
            )
        }

    }

}

object PerfumeNavigationRoutes{
    @Serializable
    data object Scan

    @Serializable
    data class PurchaseInfo(val deviceMac: String)

}
