package one.reevdev.autolingo.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import one.reevdev.autolingo.model.ExerciseType
import one.reevdev.autolingo.ui.navigation.routes.ExerciseRoute
import one.reevdev.autolingo.ui.screen.exercise.ExerciseRouter
import one.reevdev.autolingo.ui.screen.home.HomeRouter

fun NavController.navigateToMultipleChoiceScreen() {
    navigate(ExerciseRoute.MultipleChoiceExercise.route, null)
}

fun NavGraphBuilder.multipleChoiceScreen() {
    composable(ExerciseRoute.MultipleChoiceExercise.route) {
        ExerciseRouter(type = ExerciseType.Multiple)
    }
}

fun NavController.navigateToFillBlankScreen() {
    navigate(ExerciseRoute.FillBlankExercise.route, null)
}

fun NavGraphBuilder.fillBlankScreen() {
    composable(ExerciseRoute.FillBlankExercise.route) {
        ExerciseRouter(type = ExerciseType.FillBlank)
    }
}

fun NavController.navigateToHomeScreen() {
    navigate(ExerciseRoute.ExerciseHome.route, null)
}

fun NavGraphBuilder.homeScreen(
    navigateToMultipleChoice: () -> Unit,
    navigateToFillBlank: () -> Unit
) {
    composable(ExerciseRoute.ExerciseHome.route) {
        HomeRouter(
            navigateToMultipleChoice = { navigateToMultipleChoice() },
            navigateToFillBlank = { navigateToFillBlank() }
        )
    }
}