package one.reevdev.autolingo.model

import androidx.annotation.StringRes
import one.reevdev.autolingo.R

enum class ExerciseType(@StringRes val titleId: Int) {
    Multiple(R.string.title_multiple_choice_exercise), FillBlank(R.string.title_fill_blank_exercise)
}