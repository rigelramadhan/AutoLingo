package one.reevdev.autolingo.core.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import one.reevdev.autolingo.core.BuildConfig
import one.reevdev.autolingo.core.data.enumerations.QuestionType
import one.reevdev.autolingo.core.data.prompts.ModelInitialization
import one.reevdev.autolingo.core.data.prompts.QuestionPrompt
import one.reevdev.autolingo.core.data.prompts.ResponseRule
import one.reevdev.autolingo.core.data.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.remote.model.GeneratedQuestion
import one.reevdev.autolingo.core.data.utils.Resource
import one.reevdev.autolingo.core.data.utils.toKotlin
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiApi @Inject constructor() {
    private val generativeModel: GenerativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.API_KEY
        )
    }

    private val multipleQuestionHistory: MutableList<Content> = mutableListOf()
    private val fillInBlankHistory: MutableList<Content> = mutableListOf()

    init {
        multipleQuestionHistory.add(
            content {
                text(ModelInitialization.multipleChoiceSetup)
            }
        )
        fillInBlankHistory.add(
            content {
                text(ModelInitialization.fillTheBlankSetup)
            }
        )
    }

    fun getNewQuestion(type: QuestionType): Flow<Resource<GeneratedQuestion>> = flow {
        emit(Resource.Loading())
        try {
            val chat = when (type) {
                QuestionType.MultipleChoice -> generativeModel.startChat(multipleQuestionHistory)
                QuestionType.FillInBlank -> generativeModel.startChat(fillInBlankHistory)
            }

            val questionInstruction = when (type) {
                QuestionType.MultipleChoice -> QuestionPrompt.generateMultipleChoice
                QuestionType.FillInBlank -> QuestionPrompt.generateFillInBlank
            }.plus(ResponseRule.QuestionJson)

            val content = content {
                text(questionInstruction)
            }
            val response = chat.sendMessage(content).text.orEmpty()


            when (type) {
                QuestionType.MultipleChoice -> multipleQuestionHistory.apply {
                    add(content)
                    add(content("model") { text(response) })
                }
                QuestionType.FillInBlank -> fillInBlankHistory.apply {
                    add(content)
                    add(content("model") { text(response) })
                }
            }
            emit(Resource.Success(response.toKotlin(GeneratedQuestion::class.java)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty(), e))
        }
    }

    fun answerQuestion(type: QuestionType, answer: String): Flow<Resource<AnswerFeedback>> = flow {
        emit(Resource.Loading())
        try {
            val chat = when (type) {
                QuestionType.MultipleChoice -> generativeModel.startChat(multipleQuestionHistory)
                QuestionType.FillInBlank -> generativeModel.startChat(fillInBlankHistory)
            }

            val prompt = when (type) {
                QuestionType.MultipleChoice -> QuestionPrompt.requestMultipleChoiceFeedback
                QuestionType.FillInBlank -> QuestionPrompt.requestFillInBlankFeedback
            }.plus(ResponseRule.FeedbackJson)

            val content = content {
                text(prompt.plus(answer))
            }
            val response = chat.sendMessage(content).text.orEmpty()

            when (type) {
                QuestionType.MultipleChoice -> multipleQuestionHistory.apply {
                    add(content)
                    add(content("model") { text(response) })
                }
                QuestionType.FillInBlank -> fillInBlankHistory.apply {
                    add(content)
                    add(content("model") { text(response) })
                }
            }
            emit(Resource.Success(response.toKotlin(AnswerFeedback::class.java)))
        } catch(e: Exception) {
            emit(Resource.Error(e.message.orEmpty(), e))
        }
    }
}