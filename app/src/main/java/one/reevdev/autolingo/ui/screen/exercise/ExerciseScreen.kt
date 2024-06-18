package one.reevdev.autolingo.ui.screen.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import one.reevdev.autolingo.R
import one.reevdev.autolingo.model.ExerciseType
import one.reevdev.autolingo.ui.component.AppHeader
import one.reevdev.autolingo.ui.theme.AutoLingoTheme

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    type: ExerciseType,
    question: String,
    choices: List<String>? = null,
    correctAnswer: String? = null,
    feedback: String,
    hasAnswered: Boolean = false,
    isDone: Boolean,
    onSubmitAnswer: (String) -> Unit,
) {
    val (selected, onSelect) = rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        AppHeader()
        Card(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp, top = 16.dp),
                text = stringResource(type.titleId),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                text = question,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        when (type) {
            ExerciseType.Multiple -> {
                choices?.let {
                    MultipleAnswers(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 24.dp, bottom = 8.dp),
                        choices = choices,
                        selected = selected,
                        enabled = !isDone,
                        onSelect = onSelect
                    )
                }
            }

            ExerciseType.FillBlank -> {
                FillBlank(
                    answer = answer,
                    onAnswerChange = { answer = it },
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                val finalAnswer = if (type == ExerciseType.FillBlank) answer else selected
                onSubmitAnswer(finalAnswer)
            },
            enabled = !isDone
        ) {
            Text(text = "Submit")
        }
        if (hasAnswered) {
            Spacer(modifier = Modifier.height(16.dp))
            FeedbackCard(correct = isDone, feedback = feedback)
        }
    }
}

@Composable
fun MultipleAnswers(
    modifier: Modifier = Modifier,
    choices: List<String>,
    selected: String,
    enabled: Boolean,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        choices.forEach { choice ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (choice == selected),
                        onClick = { if (enabled) onSelect(choice) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    selected = choice == selected,
                    onClick = null,
                    enabled = enabled
                )
                Text(
                    text = choice,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun FillBlank(
    modifier: Modifier = Modifier,
    answer: String,
    onAnswerChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier,
            value = answer,
            onValueChange = onAnswerChange,
            label = {
                Text(text = stringResource(R.string.label_fill_blank_field))
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun FeedbackCard(
    modifier: Modifier = Modifier,
    correct: Boolean,
    feedback: String,
) {
    val containerColor = if (feedback.isBlank()) {
        MaterialTheme.colorScheme.surface
    } else if (correct) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.outlinedCardColors().copy(
            containerColor = containerColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (feedback.isBlank()) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            } else {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 16.dp),
                    painter = painterResource(R.drawable.ic_check_circle_outline),
                    contentDescription = "Answer result icon",
                    tint = if (correct) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = feedback,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseScreenPreview() {
    AutoLingoTheme {
        ExerciseScreen(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            type = ExerciseType.Multiple,
            question = "Every weekend, I look forward to spending time with my friends and family. We usually decide on an activity on Friday night, and depending on the weather, we might choose to [blank].",
            choices = listOf(
                "look up",
                "check",
                "define",
            ),
            feedback = "",
            isDone = false,
            onSubmitAnswer = {}
        )
    }
}