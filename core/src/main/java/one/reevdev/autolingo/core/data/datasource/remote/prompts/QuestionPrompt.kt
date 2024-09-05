package one.reevdev.autolingo.core.data.datasource.remote.prompts

object QuestionPrompt {
    val generateMultipleChoice = """
        Generate me a unique fill in the blank of a paragraph. The to be filled part consists of 1-3 words.
        Give me 5 choices of answers.
        This is to learn language.
    """.trimIndent()

    val generateFillInBlank = """
        Generate me a unique fill in the blank of a paragraph. The to be filled part consists of 1-3 words.
        Give me 1 correct answer.
        This is to learn language.
    """.trimIndent()

    val requestMultipleChoiceFeedback = """
        This is the user's answer (give constructive feedback to the user and why it is correct/wrong, 
        but don't give out the answer):
    """.trimIndent()

    val requestFillInBlankFeedback = """
        Give constructive feedback to the user and why it is correct/wrong. 
        If it's close in definition, you can give it as correct but explain the more appropriate answer. But if it's
        wrong grammatically, say that it's wrong and give a hint the the incorrect part is the grammar part.
        
        User's answer:
    """.trimIndent()
}