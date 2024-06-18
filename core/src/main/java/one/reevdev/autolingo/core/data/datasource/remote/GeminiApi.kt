package one.reevdev.autolingo.core.data.datasource.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import one.reevdev.autolingo.core.BuildConfig
import one.reevdev.autolingo.core.data.datasource.local.model.ChatHistory
import one.reevdev.autolingo.core.data.datasource.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.datasource.remote.model.GeneratedQuestion
import one.reevdev.autolingo.core.data.datasource.remote.prompts.ModelInitialization
import one.reevdev.autolingo.core.data.datasource.remote.prompts.QuestionPrompt
import one.reevdev.autolingo.core.data.datasource.remote.prompts.ResponseRule
import one.reevdev.autolingo.core.data.enumerations.QuestionType
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

    fun updateHistory(type: QuestionType, history: List<ChatHistory>) {
        val contents = history.map {
            content(it.role) {
                text(it.text)
            }
        }
        when (type) {
            QuestionType.MultipleChoice -> multipleQuestionHistory.apply {
                clear()
                addAll(contents)
            }
            QuestionType.FillInBlank -> fillInBlankHistory.apply {
                clear()
                addAll(contents)
            }
        }
    }

    suspend fun getNewQuestion(type: QuestionType, saveChat: suspend (List<Content>) -> Unit): GeneratedQuestion {
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

        val allContent = listOf(
            content,
            content("model") { text(response) }
        )
        when (type) {
            QuestionType.MultipleChoice -> multipleQuestionHistory.addAll(allContent)
            QuestionType.FillInBlank -> fillInBlankHistory.addAll(allContent)
        }
        saveChat(allContent)
        return response.toKotlin(GeneratedQuestion::class.java)
    }

    suspend fun answerQuestion(type: QuestionType, answer: String, saveChat: suspend (List<Content>) -> Unit): AnswerFeedback {
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
        val allContent = listOf(
            content,
            content("model") { text(response) }
        )

        when (type) {
            QuestionType.MultipleChoice -> multipleQuestionHistory.addAll(allContent)
            QuestionType.FillInBlank -> fillInBlankHistory.addAll(allContent)
        }
        saveChat(allContent)
        return response.toKotlin(AnswerFeedback::class.java)
    }
}