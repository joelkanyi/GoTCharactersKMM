package presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.AsyncImage

internal class CharacterDetailsScreen(
    private val characterId: Int,
) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val characterDetailsViewModel: CharacterDetailsViewModel by inject()

        LaunchedEffect(key1 = true) {
            characterDetailsViewModel.getCharacterDetails(characterId)
        }

        val detailsState = characterDetailsViewModel.state.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.background,
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = { navigator.pop() },
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colors.onBackground,
                                )
                            }
                            Text(
                                text = "Character Details",
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 20.sp,
                            )
                        }
                    },
                )
            },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when (val result = detailsState.value) {
                    is CharacterDetailsState.Init -> {}

                    is CharacterDetailsState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    is CharacterDetailsState.Error -> {
                        Text(
                            text = result.error,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    is CharacterDetailsState.Result -> {
                        Column {
                            Box(Modifier.fillMaxWidth().height(350.dp)) {
                                AsyncImage(
                                    imageUrl = result.character?.imageUrl ?: "",
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

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = "Full Name: ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    text = result.character?.fullName ?: "",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colors.onBackground,
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = "Family: ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    text = result.character?.family ?: "",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colors.onBackground,
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    text = "Title: ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    text = result.character?.title ?: "",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colors.onBackground,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
