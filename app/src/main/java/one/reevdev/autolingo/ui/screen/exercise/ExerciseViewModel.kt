package one.reevdev.autolingo.ui.screen.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import one.reevdev.autolingo.core.data.utils.Resource
import one.reevdev.autolingo.core.domain.usecase.ExerciseUseCase
import one.reevdev.autolingo.model.ExerciseType
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseUseCase: ExerciseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseUiState())
    val uiState: StateFlow<ExerciseUiState> by lazy { _uiState }

    fun getMultipleChoiceExercise() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            exerciseUseCase.getMultipleChoiceQuestion()
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = it.error,
                        )
                    }
                }.collect { data ->
                    when (data) {
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    error = null,
                                )
                            }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = null,
                                    type = ExerciseType.Multiple,
                                    question = data.data.question,
                                    choices = data.data.choices,
                                    answer = data.data.correctAnswer
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = data.message,
                                )
                            }
                        }
                    }
                }
        }
    }

    fun getFillBlankExercise() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            exerciseUseCase.getFillInTheBlankQuestion()
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = it.error,
                        )
                    }
                }.collect { data ->
                    when (data) {
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    error = null,
                                )
                            }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = null,
                                    type = ExerciseType.Multiple,
                                    question = data.data.question,
                                    answer = data.data.answer
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = data.message,
                                )
                            }
                        }
                    }
                }
        }
    }

    fun answerMultipleChoice(answer: String) {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            exerciseUseCase.answerMultipleChoiceQuestion(answer)
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = it.error,
                        )
                    }
                }
                .collect { data ->
                    when (data) {
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    error = null,
                                )
                            }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                if (data.data.feedback?.correct == true) {
                                    it.copy(
                                        isLoading = false,
                                        error = null,
                                        feedback = data.data.feedback?.reasoning.orEmpty(),
                                        isDone = true
                                    )
                                } else {
                                    it.copy(
                                        isLoading = false,
                                        error = null,
                                        feedback = data.data.feedback?.reasoning.orEmpty()
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = data.message,
                                )
                            }
                        }
                    }
                }
        }
    }
}


data class ExerciseUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val type: ExerciseType = ExerciseType.FillBlank,
    val question: String = "",
    val choices: List<String>? = null,
    val answer: String? = null,
    val feedback: String = "",
    val isDone: Boolean = false,
)