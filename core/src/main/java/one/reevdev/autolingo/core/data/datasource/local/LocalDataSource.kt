package one.reevdev.autolingo.core.data.datasource.local

import one.reevdev.autolingo.core.data.datasource.local.dao.ExerciseDao
import one.reevdev.autolingo.core.data.datasource.local.model.ChatHistory
import one.reevdev.autolingo.core.data.enumerations.QuestionType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val dao: ExerciseDao
) {

    suspend fun insertChat(chat: ChatHistory) {
        dao.insertChatHistory(chat)
    }

    suspend fun insertChat(chat: List<ChatHistory>) {
        dao.insertChatHistory(chat)
    }

    suspend fun getChatHistory(type: QuestionType): List<ChatHistory> {
        return when (type) {
            QuestionType.MultipleChoice -> dao.getAllMultipleChatHistory()
            QuestionType.FillInBlank -> dao.getAllFillBlankChatHistory()
        }
    }
}