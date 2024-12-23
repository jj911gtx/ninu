package io.pc7.ninu.presentation.main.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.presentation.activities.PairingActivity
import io.pc7.ninu.presentation.lab.LabMainScreen
import io.pc7.ninu.presentation.main.HomeScreen
import io.pc7.ninu.presentation.perfumeMainScreen.PerfumeMainScreen
import io.pc7.ninu.presentation.perfumeMainScreen.PerfumeMainViewModelAndroid
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

@Composable
fun MainNavigation(

    navController: NavHostController
) {



    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = MainNavigationRoutes.HomeScreen
    ){

        composable<MainNavigationRoutes.HomeScreen> {

        }


        composable<MainNavigationRoutes.HomeScreen>{
            HomeScreen(
                pair = {
                    val intent = Intent(context, PairingActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }



        composable<MainNavigationRoutes.Lab> {
            LabMainScreen(
                navBack = {navController.navigateUp()},
                navNext = { fragrances: Array<Fragrance>, intensity: Int ->
                    navController.navigate(MainNavigationRoutes.Perfume(fragrances = fragrances, intensity = intensity))
                },
            )
        }
//
//        composable<MainNavigationRoutes.Onboarding> {
//            OnboardingScreen()
//        }
//
//        composable<MainNavigationRoutes.WhereTo>{
//            PerfumeSelectionScreen(viewModel = koinViewModel<WhereToViewModeAndroid>().viewModel)
//        }
//        composable<MainNavigationRoutes.FeelHow>{
//            PerfumeSelectionScreen(viewModel = koinViewModel<FeelHowViewModelAndroid>().viewModel)
//        }
//
//
//        composable<MainNavigationRoutes.Profile> {
//            ProfileScreen()
//        }
//        composable<MainNavigationRoutes.Language> {
//            LanguageSelectionScreen()
//        }
//
//        composable<MainNavigationRoutes.FAQ> {
//            FaqContactUsScreen()
//        }
//        composable<MainNavigationRoutes.Follow> {
//            FollowScreen()
//        }
//
//
//        composable<MainNavigationRoutes.Statistics> {
//            StatisticsScreen()
//        }
//
        composable<MainNavigationRoutes.Perfume>(
            typeMap = mapOf(
                typeOf<Array<Fragrance>>() to CustomNavType.fragranceArrayType,
            )
        ) {
            val perfume = it.toRoute<MainNavigationRoutes.Perfume>()
            PerfumeMainScreen(
                navBack = { navController.navigateUp() },
                viewModel = koinViewModel<PerfumeMainViewModelAndroid>(parameters = { parametersOf(perfume.fragrances, perfume.intensity) }).viewModel,
                navToLab = { navController.navigate(MainNavigationRoutes.Lab) },
                navHome = {
                    navController.navigate(MainNavigationRoutes.HomeScreen){
                        popUpTo(MainNavigationRoutes.HomeScreen)
                    }
                }
            )
        }
    }



}
