package com.massa.petmedtracking.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.massa.petmedtracking.presentation.ui.Screen
import com.massa.petmedtracking.presentation.ui.detail.PetDetailScreen
import com.massa.petmedtracking.presentation.ui.home.HomeScreen

@Composable
fun PetMedNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onNavigateToPetDetails = { petId ->
                    navController.navigate(Screen.PetDetail(petId))
                }
            )
        }
        
        composable<Screen.PetDetail> { backStackEntry ->
            val petDetail: Screen.PetDetail = backStackEntry.toRoute()
            PetDetailScreen(
                petId = petDetail.petId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
