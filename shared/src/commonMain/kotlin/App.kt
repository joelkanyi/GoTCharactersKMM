import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.characters.CharactersScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(CharactersScreen())
    }
}

expect fun getPlatformName(): String
