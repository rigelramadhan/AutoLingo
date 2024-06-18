package one.reevdev.autolingo.core.data.datasource.remote.prompts

object ResponseRule {
    private const val JSON_GENERAL_RULE= """
        Please return a raw JSON response with the following structure without any formatting because 
        it will be deserialized by GSON:
    """

    val QuestionJson = """
        $JSON_GENERAL_RULE
        
        {
          "status": "success/error",
          "data": {
            "question": "While studying for my English exam, I came across an unfamiliar word. So, I decided to [blank] it in the dictionary.",
            "answers": [
              {
                "text": "look up",
                "correct": false,
                "explanation": "Look up is broader and can be used for finding information in various sources."
              },
              {
                "text": "check",
                "correct": true,
                "explanation": "Check emphasizes consulting the dictionary specifically."
              },
              {
                "text": "define",
                "correct": false,
                "explanation": "Define focuses on the formal definition, which might not be the immediate need."
              }
            ]
          }
        }
    """.trimIndent()

    val FeedbackJson = """
        $JSON_GENERAL_RULE
        
        {
          "userAnswer": "<answer_here>",
          "feedback": {
            "correct": <true/false>,
            "reasoning": "<explanation_why_answer_is_correct/incorrect>"
          }
        }
    """.trimIndent()
}