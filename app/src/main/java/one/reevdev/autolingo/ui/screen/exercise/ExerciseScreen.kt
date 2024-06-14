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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import one.reevdev.autolingo.R
import one.reevdev.autolingo.model.ExerciseType
import one.reevdev.autolingo.ui.theme.AutoLingoTheme

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    type: ExerciseType,
    question: String,
    choices: List<String>? = null
) {
    var hasAnswered by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
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
                    val (selected, onSelect) = rememberSaveable { mutableStateOf("") }
                    MultipleAnswers(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 16.dp),
                        choices = choices,
                        selected = selected,
                        onSelect = onSelect
                    )
                }
            }

            ExerciseType.FillBlank -> {
                var answer by rememberSaveable { mutableStateOf("") }
                FillBlank(
                    answer = answer,
                    onAnswerChange = { answer = it }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                hasAnswered = true
            },
        ) {
            Text(text = "Submit")
        }
        if (hasAnswered) {
            Spacer(modifier = Modifier.height(16.dp))
            FeedbackCard(correct = false, feedback = "It can be better")
        }
    }
}

@Composable
fun MultipleAnswers(
    modifier: Modifier = Modifier,
    choices: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        choices.forEach { choice ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (choice == selected),
                        onClick = { onSelect(choice) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    selected = choice == selected,
                    onClick = null
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
    ) {
        TextField(
            modifier = Modifier,
            value = answer,
            onValueChange = onAnswerChange,
            label = {
                Text(text = stringResource(R.string.label_fill_blank_field))
            }
        )
    }
}

@Composable
fun FeedbackCard(
    modifier: Modifier = Modifier,
    correct: Boolean,
    feedback: String,
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 4.dp),
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
            )
        )
    }
}