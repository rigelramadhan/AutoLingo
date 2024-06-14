package one.reevdev.autolingo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import one.reevdev.autolingo.ui.navigation.fillBlankScreen
import one.reevdev.autolingo.ui.navigation.homeScreen
import one.reevdev.autolingo.ui.navigation.multipleChoiceScreen
import one.reevdev.autolingo.ui.navigation.navigateToFillBlankScreen
import one.reevdev.autolingo.ui.navigation.navigateToMultipleChoiceScreen
import one.reevdev.autolingo.ui.navigation.routes.ExerciseRoute

@Composable
fun AutoLingoApp(
    modifier: Modifier = Modifier,
    startDestination: String = ExerciseRoute.ExerciseHome.route,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            navigateToMultipleChoice = { navController.navigateToMultipleChoiceScreen() },
            navigateToFillBlank = { navController.navigateToFillBlankScreen() },
        )
        multipleChoiceScreen()
        fillBlankScreen()
    }
}