package one.reevdev.autolingo.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import one.reevdev.autolingo.R
import one.reevdev.autolingo.ui.theme.AutoLingoTheme

@Composable
fun ModeCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    @DrawableRes icon: Int? = null,
    @DrawableRes drawable: Int? = null,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                )
            )
        }
    }
}

@Preview
@Composable
private fun ModeCardMPPreview() {
    AutoLingoTheme {
        ModeCard(
            title = "Multiple Choice!",
            description = "Train your vocabulary with multiple choice questions.",
            icon = R.drawable.ic_launcher_foreground
        ) {

        }
    }
}