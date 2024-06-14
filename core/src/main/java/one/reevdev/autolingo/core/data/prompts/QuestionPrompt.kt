package one.reevdev.autolingo.core.data.prompts

object QuestionPrompt {
    val generateMultipleChoice = """
        Generate me a fill in the blank of a paragraph. The to be filled part consists of 1-3 words.
        Give me 5 choices of answers.
        This is to learn language.
    """.trimIndent()

    val generateFillInBlank = """
        Generate me a fill in the blank of a paragraph. The to be filled part consists of 1-3 words.
        Give me 1 correct answer.
        This is to learn language.
    """.trimIndent()

    val requestMultipleChoiceFeedback = """
        
    """.trimIndent()

    val requestFillInBlankFeedback = """
        
    """.trimIndent()
}