package one.reevdev.autolingo.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class GeneratedQuestion(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AnswersItem(

	@field:SerializedName("correct")
	val correct: Boolean? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("explanation")
	val explanation: String? = null
)

data class Data(

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("answers")
	val answers: List<AnswersItem>? = null
)
