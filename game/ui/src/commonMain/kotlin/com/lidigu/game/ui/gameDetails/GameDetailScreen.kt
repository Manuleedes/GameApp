package com.lidigu.game.ui.gameDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalUriHandler
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameDetailsScreen(modifier: Modifier = Modifier,
                      id: String,
                      onDeleteSuccess: () -> Unit,
                      onBackClick: () -> Unit) {

    val viewModel = koinViewModel<GameDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.getGameDetails(id.toInt())
    }

    LaunchedEffect(uiState.value.isDeleted) {
        if (uiState.value.isDeleted) {
            onDeleteSuccess()
            viewModel.consumeDeleteEvent()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GameDetailsScreenContent(
            modifier = modifier.fillMaxSize(), uiState = uiState.value,
            onDelete = { viewModel.delete(it) },
            onSave = { id, name, image -> viewModel.toggleFavorite(id, image, name) },
            onDownload = { id, name, image ->
                val gameUrl = uiState.value.data?.stores?.firstOrNull { it.url?.isNotBlank() == true }?.url
                    ?: uiState.value.data?.website ?: ""
                viewModel.toggleDownload(id, image, name, gameUrl)
            },
            onSaveReview = { id, name, image, rating, review -> viewModel.saveReview(id, image, name, rating, review) },
            onPlay = { isPlaying = true },
            isSaved = uiState.value.isSaved,
            onBackClick = onBackClick
        )

        if (isPlaying) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black).clickable { isPlaying = false }, contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Playing ${uiState.value.data?.name ?: "Game"} (Offline Mode)...", color = Color.White, style = MaterialTheme.typography.headlineLarge)
                    Spacer(Modifier.height(20.dp))
                    IconButton(onClick = { isPlaying = false }, modifier = Modifier.background(Color.White, CircleShape)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameDetailsScreenContent(
    modifier: Modifier = Modifier, uiState: GameDetailsScreen.UiState,
    onDelete: (Int) -> Unit,
    onSave: (id: Int, title: String, image: String) -> Unit,
    onDownload: (id: Int, title: String, image: String) -> Unit,
    onSaveReview: (id: Int, title: String, image: String, rating: Int, review: String) -> Unit,
    onPlay: () -> Unit,
    onBackClick: () -> Unit,
    isSaved: Boolean
) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (uiState.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                        text = data.name ?: "",
                        style = MaterialTheme.typography.displaySmall

                    )
                }
                item {
                    Text(text = data.description ?: "", style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).fillMaxWidth())
                }
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Platforms:", style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 12.dp).padding(top = 24.dp)
                        )

                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(data.platforms){
                                Card(
                                    modifier = Modifier.padding(12.dp).wrapContentSize(),
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                                ){
                                    Column(modifier = Modifier.width(150.dp)) {
                                        AsyncImage(model = it.image, contentDescription = null,
                                            modifier = Modifier.background(color = Color.Transparent,
                                                shape = CircleShape
                                            ).clip(CircleShape)
                                        )
                                        Text(
                                            modifier = Modifier.padding(vertical = 8.dp),
                                            text = it.name ?: "",
                                            style = MaterialTheme.typography.headlineSmall
                                        )
                                    }
                                }

                            }

                        }

                    }
                }

                item {
                    Text(
                        text = "Stores", style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(horizontal = 12.dp).padding(top = 24.dp)
                            .padding(bottom = 12.dp)
                    )
                }

                items(data.stores) { store ->
                    val uriHandler = LocalUriHandler.current
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp)
                            .padding(bottom = 8.dp).fillMaxWidth()
                            .clickable {
                                store.url?.let { if (it.isNotBlank()) uriHandler.openUri(it) }
                            }
                    ) {

                        AsyncImage(
                            model = store.image, contentDescription = null,
                            modifier = Modifier.size(120.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                ).clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = store.name ?: "", style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = store.domain ?: "", style = MaterialTheme.typography.bodySmall,
                                textDecoration = TextDecoration.Underline
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Game count: " + store.gameCount,
                                style = MaterialTheme.typography.headlineSmall
                            )

                        }

                    }
                }


                item {
                    Text(
                        text = "Tags", style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(horizontal = 12.dp).padding(top = 24.dp)
                    )
                }

                item {
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
                    ) {

                        data.tags.forEach {
                            Row(
                                modifier = Modifier.padding(top = 8.dp, end = 12.dp).background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(200.dp)
                                ).border(
                                    width = .5.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(200.dp)
                                )
                                    .clip(RoundedCornerShape(200.dp)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = it.image,
                                    contentDescription = null,
                                    modifier = Modifier.size(35.dp)
                                        .background(color = Color.Transparent, shape = CircleShape)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = it.name ?: "", style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }

                    }
                }


                item {
                    Text(
                        text = "Developers", style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 12.dp)
                            .padding(top = 24.dp, bottom = 12.dp)
                    )
                }

                items(data.developers) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {

                        AsyncImage(
                            model = it.image, contentDescription = null,
                            modifier = Modifier.size(120.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                ).clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = it.name ?: "", style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Game count: " + it.gameCount,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }

                }


                item {
                    Text(
                        text = "Review & Rating", style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(horizontal = 12.dp).padding(top = 24.dp)
                    )
                }

                item {
                    var rating by remember { mutableStateOf(uiState.rating ?: 0) }
                    var reviewText by remember { mutableStateOf(uiState.review ?: "") }

                    Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                        Row {
                            (1..5).forEach { index ->
                                IconButton(onClick = { rating = index }) {
                                    Icon(
                                        imageVector = if (index <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = null,
                                        tint = if (index <= rating) Color(0xFFFFD700) else Color.Gray
                                    )
                                }
                            }
                        }

                        TextField(
                            value = reviewText,
                            onValueChange = { reviewText = it },
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            placeholder = { Text("Write your review here...") }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                onSaveReview(data.id, data.name ?: "", data.backgroundImage ?: "", rating, reviewText)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Submit Review")
                        }
                    }
                }


            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        onSave(data.id, data.name ?: "", data.backgroundImage ?: "")
                    },
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp),
                        tint = if (isSaved) Color.Red else Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = {
                        onDownload(data.id, data.name ?: "", data.backgroundImage ?: "")
                    },
                    modifier = Modifier.background(color = Color.White, shape = CircleShape),
                    enabled = !uiState.isDownloading
                ) {
                    Icon(
                        imageVector = if (uiState.isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp),
                        tint = when {
                            uiState.isDownloading -> Color.Blue
                            uiState.isDownloaded -> Color.Green
                            else -> Color.Gray
                        }
                    )
                }
                
                // Show download progress
                if (uiState.isDownloading && uiState.downloadProgress != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.size(40.dp).background(color = Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { uiState.downloadProgress.progress },
                            modifier = Modifier.size(30.dp),
                            color = Color.Blue,
                        )
                        Text(
                            text = "${(uiState.downloadProgress.progress * 100).toInt()}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Blue
                        )
                    }
                }

                if (uiState.isDownloaded) {
                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = onPlay,
                        modifier = Modifier.background(color = Color(0xFF4CAF50), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow, contentDescription = "Play",
                            modifier = Modifier.padding(4.dp),
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))


                IconButton(
                    onClick = {
                        onDelete(data.id)
                    },
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = null,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            }

        }
    }
}