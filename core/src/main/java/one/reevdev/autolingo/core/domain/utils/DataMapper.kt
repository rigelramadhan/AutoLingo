package one.reevdev.autolingo.core.domain.utils

import one.reevdev.autolingo.core.data.remote.model.GeneratedQuestion
import one.reevdev.autolingo.core.domain.model.FillInTheBlankQuestion
import one.reevdev.autolingo.core.domain.model.MultipleChoiceQuestion

fun GeneratedQuestion.toMultipleChoiceDomain() = MultipleChoiceQuestion(
    question = this.data?.question.orEmpty(),
    choices = data?.answers?.map { it.text.orEmpty() }.orEmpty(),
    correctAnswer = data?.answers?.firstOrNull { it.correct == true }?.text.orEmpty()
)

fun GeneratedQuestion.toFillInTheBlankDomain() = FillInTheBlankQuestion(
    question = this.data?.question.orEmpty(),
    answer = data?.answers?.firstOrNull { it.correct == true }?.text.orEmpty()
)