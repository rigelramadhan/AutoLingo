package one.reevdev.autolingo.core.domain.model

data class MultipleChoiceQuestion(
    val question: String,
    val choices: List<String>,
    val correctAnswer: String,
)