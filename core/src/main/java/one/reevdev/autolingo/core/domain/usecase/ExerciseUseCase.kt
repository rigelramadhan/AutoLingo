package one.reevdev.autolingo.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import one.reevdev.autolingo.core.data.datasource.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.utils.Resource
import one.reevdev.autolingo.core.domain.model.FillInTheBlankQuestion
import one.reevdev.autolingo.core.domain.model.MultipleChoiceQuestion

interface ExerciseUseCase {
    fun getMultipleChoiceQuestion(): Flow<Resource<MultipleChoiceQuestion>>
    fun getFillInTheBlankQuestion(): Flow<Resource<FillInTheBlankQuestion>>
    fun answerMultipleChoiceQuestion(answer: String): Flow<Resource<AnswerFeedback>>
    fun answerFillInTheBlankQuestion(answer: String): Flow<Resource<AnswerFeedback>>
}