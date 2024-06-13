package one.reevdev.autolingo.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class AnswerFeedback(

	@field:SerializedName("feedback")
	val feedback: Feedback? = null,

	@field:SerializedName("userAnswer")
	val userAnswer: String? = null
)

data class Feedback(

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("reasoning")
	val reasoning: String? = null
)
