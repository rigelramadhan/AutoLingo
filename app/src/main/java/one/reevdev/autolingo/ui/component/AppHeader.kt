package one.reevdev.autolingo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import one.reevdev.autolingo.R

@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
        textAlign = TextAlign.Center
    )
}