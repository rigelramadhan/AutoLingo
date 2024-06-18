package one.reevdev.autolingo.core.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_history")
data class ChatHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "role")
    val role: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "type")
    val type: String,
)
