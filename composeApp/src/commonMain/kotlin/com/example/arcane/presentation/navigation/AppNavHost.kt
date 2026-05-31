package com.example.arcane.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.arcane.presentation.screens.bookdetail.BookDetailScreen
import com.example.arcane.presentation.screens.explore.ExploreScreen
import com.example.arcane.presentation.screens.home.HomeScreen
import com.example.arcane.presentation.screens.ai.AIAssistantScreen
import com.example.arcane.presentation.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val navigationActions = createNavigationActions(navController)

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreen(
                onNavigateToExplore = { navigationActions.navigateToExplore() },
                onNavigateToBook = { googleBookId, localBookId ->
                    navigationActions.navigateToBookDetail(googleBookId, localBookId)
                },
                onNavigateToAI = { navigationActions.navigateToResearchAssistant("", "") },
                onNavigateToSettings = { navigationActions.navigateToSettings() }
            )
        }

        composable<Route.Explore> {
            ExploreScreen(
                onNavigateBack = { navigationActions.navigateBack() },
                onNavigateToBook = { idDariExplore ->
                    navigationActions.navigateToBookDetail(
                        googleBookId = idDariExplore,
                        localBookId = 0L
                    )
                }
            )
        }

        composable<Route.BookDetail> { backStackEntry ->
            val route: Route.BookDetail = backStackEntry.toRoute()
            BookDetailScreen(
                googleBookId = route.googleBookId,
                localBookId = route.localBookId,
                onNavigateBack = { navigationActions.navigateBack() },
                onNavigateToResearch = { title, description ->
                    navigationActions.navigateToResearchAssistant(title, description)
                }
            )
        }

        composable<Route.ResearchAssistant> { backStackEntry ->
            val route: Route.ResearchAssistant = backStackEntry.toRoute()
            AIAssistantScreen(
                initialText = "Buku: ${route.bookTitle}\n\nDeskripsi: ${route.bookDescription}",
                onNavigateBack = { navigationActions.navigateBack() }
            )
        }

        composable<Route.Settings> {
            SettingsScreen(
                onNavigateBack = { navigationActions.navigateBack() }
            )
        }
    }
}

private fun createNavigationActions(navController: NavHostController): NavigationActions {
    return object : NavigationActions {
        override fun navigateToHome() {
            navController.navigate(Route.Home) {
                popUpTo(Route.Home) { inclusive = true }
            }
        }

        override fun navigateToExplore() {
            navController.navigate(Route.Explore)
        }

        override fun navigateToBookDetail(googleBookId: String, localBookId: Long) {
            navController.navigate(Route.BookDetail(googleBookId, localBookId))
        }

        override fun navigateToResearchAssistant(bookTitle: String, bookDescription: String) {
            navController.navigate(Route.ResearchAssistant(bookTitle, bookDescription))
        }

        override fun navigateToSettings() {
            navController.navigate(Route.Settings)
        }

        override fun navigateBack() {
            navController.popBackStack()
        }
    }
}