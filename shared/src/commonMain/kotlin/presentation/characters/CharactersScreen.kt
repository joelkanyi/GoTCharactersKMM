package presentation.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.GoTCharacter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.details.CharacterDetailsScreen
import utils.AsyncImage

internal class CharactersScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val characterViewModel: CharactersViewModel by inject()
        val charactersState = characterViewModel.state.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.background,
                ) {
                    Text(
                        text = "GoT Characters",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (val result = charactersState.value) {
                    is CharactersState.Init -> {}

                    is CharactersState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    is CharactersState.Error -> {
                        Text(
                            text = result.error,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    is CharactersState.Result -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(result.characters) { character ->
                                CharacterCard(
                                    character = character,
                                    onCardClick = { id ->
                                        navigator.push(CharacterDetailsScreen(id))
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: GoTCharacter,
    onCardClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier.padding(horizontal = 4.dp),
        onClick = {
            onCardClick(character.id)
        },
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            Box(modifier.fillMaxWidth().height(150.dp)) {
                AsyncImage(
                    imageUrl = character.imageUrl,
                    contentDescription = "GOT character",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    loadingPlaceHolder = {},
                    errorPlaceHolder = {},
                    alignment = Alignment.Center,
                    alpha = DefaultAlpha,
                    coloFilter = null,
                    filterQuality = DrawScope.DefaultFilterQuality,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = character.fullName,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
