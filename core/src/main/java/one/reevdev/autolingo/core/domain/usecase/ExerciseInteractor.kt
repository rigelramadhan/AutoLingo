package one.reevdev.autolingo.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import one.reevdev.autolingo.core.data.remote.model.AnswerFeedback
import one.reevdev.autolingo.core.data.repository.IExerciseRepository
import one.reevdev.autolingo.core.data.utils.Resource
import one.reevdev.autolingo.core.domain.model.FillInTheBlankQuestion
import one.reevdev.autolingo.core.domain.model.MultipleChoiceQuestion
import one.reevdev.autolingo.core.domain.utils.toFillInTheBlankDomain
import one.reevdev.autolingo.core.domain.utils.toMultipleChoiceDomain
import javax.inject.Inject

class ExerciseInteractor @Inject constructor(
    private val repository: IExerciseRepository
) : ExerciseUseCase {
    override fun getMultipleChoiceQuestion(): Flow<Resource<MultipleChoiceQuestion>> {
        return repository.getMultipleChoiceQuestion().map {
            when(it) {
                is Resource.Error -> Resource.Error(it.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> Resource.Success(it.data.toMultipleChoiceDomain())
            }
        }
    }

    override fun getFillInTheBlankQuestion(): Flow<Resource<FillInTheBlankQuestion>> {
        return repository.getFillInTheBlankQuestion().map {
            when(it) {
                is Resource.Error -> Resource.Error(it.message)
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> Resource.Success(it.data.toFillInTheBlankDomain())
            }
        }
    }

    override fun answerMultipleChoiceQuestion(answer: String): Flow<Resource<AnswerFeedback>> {
        return repository.answerMultipleChoiceQuestion(answer)
    }

    override fun answerFillInTheBlankQuestion(answer: String): Flow<Resource<AnswerFeedback>> {
        return repository.answerFillInTheBlankQuestion(answer)
    }
}