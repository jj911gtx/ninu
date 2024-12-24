package io.pc7.ninu.presentation.main.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.pc7.ninu.data.network.model.navigation.NavigatePerfumeMain
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.presentation.activities.PairingActivity
import io.pc7.ninu.presentation.lab.LabMainScreen
import io.pc7.ninu.presentation.main.HomeScreen
import io.pc7.ninu.presentation.perfumeDetails.PerfumeMainScreen
import io.pc7.ninu.presentation.perfumeDetails.PerfumeMainViewModelAndroid
import io.pc7.ninu.presentation.perfumeSave.PerfumeSaveScreen
import io.pc7.ninu.presentation.perfumeSelection.PerfumeSelectionScreen
import io.pc7.ninu.presentation.perfumeSelection.WhereToViewModeAndroid
import io.pc7.ninu.presentation.settings.settingsNavigation
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
                    navController.navigate(MainNavigationRoutes.PerfumeSave(fragrances = fragrances))
                },
            )
        }
        composable<MainNavigationRoutes.PerfumeSave>(
            typeMap = mapOf(
                typeOf<Array<Fragrance>>() to CustomNavType.fragranceArrayType,
            )
        ) {
            val fragrances = it.toRoute<MainNavigationRoutes.PerfumeSave>()
            PerfumeSaveScreen(
                navBack = {navController.navigateUp()},
                viewModel = koinViewModel<PerfumeMainViewModelAndroid>(parameters = { parametersOf(NavigatePerfumeMain(
                    fragrances = fragrances.fragrances,
                    id = 1,
                    name = ""
                ), ) }).viewModel,
                navToLab = {navController.navigateUp()},
                navHome = {navController.navigate(MainNavigationRoutes.HomeScreen){
                    popUpTo<MainNavigationRoutes.HomeScreen>()
                }

              },

            )
        }


        settingsNavigation(navController)
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

        composable<MainNavigationRoutes.WhereTo>{
            PerfumeSelectionScreen(
                viewModel = koinViewModel<WhereToViewModeAndroid>().viewModel,
                navToPerfume = { fragrances: MainNavigationRoutes.PerfumeDetails ->
                    navController.navigate(fragrances)
                }
            )
        }
//        composable<MainNavigationRoutes.FeelHow>{
//            PerfumeSelectionScreen(viewModel = koinViewModel<FeelHowViewModelAndroid>().viewModel,
//                navToPerfume = { fragrances: Array<Fragrance> ->
//                    navController.navigate(MainNavigationRoutes.Perfume(fragrances = fragrances))
//                }
//            )
//        }

        composable<MainNavigationRoutes.PerfumeDetails>(
            typeMap = mapOf(
                typeOf<Array<Fragrance>>() to CustomNavType.fragranceArrayType,
            )
        ) {
            val perfume = it.toRoute<MainNavigationRoutes.PerfumeDetails>()
            PerfumeMainScreen(
                navBack = { navController.navigateUp() },
                viewModel = koinViewModel<PerfumeMainViewModelAndroid>(parameters = { parametersOf(NavigatePerfumeMain(
                    fragrances = perfume.fragrances,
                    id = perfume.id,
                    name = perfume.name
                ), ) }).viewModel,
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
