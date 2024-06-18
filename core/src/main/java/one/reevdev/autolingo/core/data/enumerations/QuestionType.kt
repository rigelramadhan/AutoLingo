package one.reevdev.autolingo.core.data.enumerations

enum class QuestionType(val key: String) {
    MultipleChoice(QuestionConstant.MULTIPLE_TYPE),
    FillInBlank(QuestionConstant.FILL_TYPE)
}