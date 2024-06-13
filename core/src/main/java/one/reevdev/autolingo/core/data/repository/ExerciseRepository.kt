package one.reevdev.autolingo.core.data.repository

import kotlinx.coroutines.flow.Flow
import one.reevdev.autolingo.core.data.enumerations.QuestionType
import one.reevdev.autolingo.core.data.remote.GeminiApi
import one.reevdev.autolingo.core.data.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.remote.model.GeneratedQuestion
import one.reevdev.autolingo.core.data.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    private val gemini: GeminiApi
) : IExerciseRepository {
    override fun getMultipleChoiceQuestion(): Flow<Resource<GeneratedQuestion>> {
        return gemini.getNewQuestion(QuestionType.MultipleChoice)
    }

    override fun getFillInTheBlankQuestion(): Flow<Resource<GeneratedQuestion>> {
        return gemini.getNewQuestion(QuestionType.FillInBlank)
    }

    override fun answerMultipleChoiceQuestion(answer: String): Flow<Resource<AnswerFeedback>> {
        return gemini.answerQuestion(QuestionType.MultipleChoice, answer)
    }

    override fun answerFillInTheBlankQuestion(answer: String): Flow<Resource<AnswerFeedback>> {
        return gemini.answerQuestion(QuestionType.FillInBlank, answer)
    }
}