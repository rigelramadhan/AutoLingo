package one.reevdev.autolingo.core.data.utils

import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import one.reevdev.autolingo.core.data.datasource.local.model.ChatHistory
import one.reevdev.autolingo.core.data.enumerations.QuestionType

fun Content.toChatHistory(type: QuestionType): ChatHistory = ChatHistory(
    role = role.orEmpty(),
    text = (parts.firstOrNull() as? TextPart)?.text.orEmpty(),
    type = type.key,
)