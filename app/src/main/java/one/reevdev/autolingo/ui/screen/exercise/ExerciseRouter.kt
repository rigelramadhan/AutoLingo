package one.reevdev.autolingo.ui.screen.exercise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import one.reevdev.autolingo.model.ExerciseType

@Composable
fun ExerciseRouter(
    modifier: Modifier = Modifier,
    viewModel: ExerciseViewModel = hiltViewModel(),
    type: ExerciseType,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        when (type) {
            ExerciseType.Multiple -> viewModel.getMultipleChoiceExercise()
            ExerciseType.FillBlank -> viewModel.getFillBlankExercise()
        }
    }

    if (uiState.question.isNotBlank()) {
        ExerciseScreen(
            modifier = modifier,
            type = type,
            question = uiState.question,
            choices = uiState.choices,
            correctAnswer = uiState.answer,
            feedback = uiState.feedback,
            isDone = uiState.isDone,
            onSubmitAnswer = {
                viewModel.answerMultipleChoice(it)
            }
        )
    }
}