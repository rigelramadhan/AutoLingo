package one.reevdev.autolingo.core.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import one.reevdev.autolingo.core.data.datasource.local.model.ChatHistory
import one.reevdev.autolingo.core.data.enumerations.QuestionConstant

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM chat_history WHERE type = \"${QuestionConstant.MULTIPLE_TYPE}\"")
    suspend fun getAllMultipleChatHistory(): List<ChatHistory>

    @Query("SELECT * FROM chat_history WHERE type = \"${QuestionConstant.FILL_TYPE}\"")
    suspend fun getAllFillBlankChatHistory(): List<ChatHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ChatHistory::class)
    suspend fun insertChatHistory(chatHistory: ChatHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ChatHistory::class)
    suspend fun insertChatHistory(chatHistory: List<ChatHistory>)

}