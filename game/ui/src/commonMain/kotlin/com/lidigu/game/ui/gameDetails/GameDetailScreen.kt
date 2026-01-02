package com.lidigu.game.ui.gameDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameDetailsScreen(modifier: Modifier = Modifier){
    val viewModel = koinViewModel<GameDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    GameDetailsScreenContent(modifier = modifier.fillMaxSize(),uiState= uiState.value)
}


@Composable
fun GameDetailsScreenContent(
    modifier: Modifier = Modifier, uiState: GameDetailsScreen.UiState
){
   if (uiState.isLoading) {
       Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
           CircularProgressIndicator()
       }
   }
    if (uiState.error.isNotBlank()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(uiState.error)
        }

    }

    uiState.data?.let { data ->
        Box(modifier.fillMaxSize()){
            LazyColumn(modifier = Modifier.fillMaxSize()){
                item {
                    AsyncImage(model = data.backgroundImage, contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(350.dp),
                        contentScale = ContentScale.Crop)
                }
                item {
                    Text( modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp).fillMaxWidth(),
                        text = data.name,
                        style = MaterialTheme.typography.displayMedium

                    )
                }
            }

        }
    }
}