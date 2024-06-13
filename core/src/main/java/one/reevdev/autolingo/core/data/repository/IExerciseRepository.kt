package one.reevdev.autolingo.core.data.repository

import kotlinx.coroutines.flow.Flow
import one.reevdev.autolingo.core.data.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.remote.model.GeneratedQuestion
import one.reevdev.autolingo.core.data.utils.Resource

interface IExerciseRepository {
    fun getMultipleChoiceQuestion(): Flow<Resource<GeneratedQuestion>>
    fun getFillInTheBlankQuestion(): Flow<Resource<GeneratedQuestion>>
    fun answerMultipleChoiceQuestion(answer: String): Flow<Resource<AnswerFeedback>>
    fun answerFillInTheBlankQuestion(answer: String): Flow<Resource<AnswerFeedback>>
}