package one.reevdev.autolingo.ui.navigation.routes

sealed class ExerciseRoute(val route: String) {
    data object ExerciseHome : ExerciseRoute(RouteConstants.ROUTE_HOME)
    data object MultipleChoiceExercise : ExerciseRoute(RouteConstants.ROUTE_MULTIPLE_EXERCISE)
    data object FillBlankExercise : ExerciseRoute(RouteConstants.ROUTE_FILL_BLANK_EXERCISE)
}