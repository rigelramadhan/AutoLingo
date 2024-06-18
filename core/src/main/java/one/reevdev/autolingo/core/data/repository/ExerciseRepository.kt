package one.reevdev.autolingo.core.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import one.reevdev.autolingo.core.data.datasource.local.LocalDataSource
import one.reevdev.autolingo.core.data.datasource.remote.GeminiApi
import one.reevdev.autolingo.core.data.datasource.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.datasource.remote.model.GeneratedQuestion
import one.reevdev.autolingo.core.data.enumerations.QuestionType
import one.reevdev.autolingo.core.data.utils.Resource
import one.reevdev.autolingo.core.data.utils.toChatHistory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    private val gemini: GeminiApi,
    private val localDataSource: LocalDataSource,
) : IExerciseRepository {
    override fun getMultipleChoiceQuestion(): Flow<Resource<GeneratedQuestion>> = flow {
        emit(Resource.Loading())
        val type = QuestionType.MultipleChoice
        gemini.apply {
            CoroutineScope(Dispatchers.IO).launch {
                updateHistory(type, localDataSource.getChatHistory(type))
            }
            emit(Resource.Success(getNewQuestion(type) { contents ->
                localDataSource.insertChat(
                    contents.map {
                        it.toChatHistory(type)
                    },
                )
            }))
        }
    }

    override fun getFillInTheBlankQuestion(): Flow<Resource<GeneratedQuestion>> = flow {
        emit(Resource.Loading())
        val type = QuestionType.FillInBlank
        gemini.apply {
            updateHistory(type, localDataSource.getChatHistory(type))
            emit(Resource.Success(getNewQuestion(type) { contents ->
                localDataSource.insertChat(contents.map {
                    it.toChatHistory(type)
                })
            }))
        }
    }

    override fun answerMultipleChoiceQuestion(answer: String): Flow<Resource<AnswerFeedback>> =
        flow {
            emit(Resource.Loading())
            val type = QuestionType.MultipleChoice
            gemini.apply {
                updateHistory(type, localDataSource.getChatHistory(type))
                emit(Resource.Success(
                    answerQuestion(type, answer) { contents ->
                        localDataSource.insertChat(contents.map {
                            it.toChatHistory(type)
                        })
                    }
                ))
            }
        }

    override fun answerFillInTheBlankQuestion(answer: String): Flow<Resource<AnswerFeedback>> =
        flow {
            emit(Resource.Loading())
            val type = QuestionType.FillInBlank
            gemini.apply {
                updateHistory(type, localDataSource.getChatHistory(type))
                emit(Resource.Success(answerQuestion(type, answer) { contents ->
                    localDataSource.insertChat(contents.map {
                        it.toChatHistory(type)
                    })
                }))
            }
        }
}