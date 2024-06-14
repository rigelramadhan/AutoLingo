package one.reevdev.autolingo.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeRouter(
    modifier: Modifier = Modifier,
    navigateToMultipleChoice: () -> Unit,
    navigateToFillBlank: () -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        navigateToMultipleChoice = navigateToMultipleChoice,
        navigateToFillBlank = navigateToFillBlank,
    )
}