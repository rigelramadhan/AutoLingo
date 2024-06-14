package one.reevdev.autolingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import one.reevdev.autolingo.ui.AutoLingoApp
import one.reevdev.autolingo.ui.theme.AutoLingoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoLingoTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    AutoLingoApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}