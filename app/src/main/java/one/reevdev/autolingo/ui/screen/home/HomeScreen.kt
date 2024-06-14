package one.reevdev.autolingo.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import one.reevdev.autolingo.R
import one.reevdev.autolingo.ui.component.AppHeader
import one.reevdev.autolingo.ui.component.ModeCard
import one.reevdev.autolingo.ui.theme.AutoLingoTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToMultipleChoice: () -> Unit = {},
    navigateToFillBlank: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppHeader()
        ModeCard(
            title = stringResource(R.string.title_card_multiple_choice),
            description = stringResource(R.string.description_card_multiple_choice),
            onClick = navigateToMultipleChoice
        )
        ModeCard(
            title = stringResource(R.string.title_card_fill_blank),
            description = stringResource(R.string.description_card_fill_blank),
            onClick = navigateToFillBlank
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AutoLingoTheme {
        HomeScreen()
    }
}