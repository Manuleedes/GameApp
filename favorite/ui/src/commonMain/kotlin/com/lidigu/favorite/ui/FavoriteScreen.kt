package com.lidigu.favorite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.IconButton
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lidigu.common.domain.model.Game
import com.lidigu.common.ui.listItem.GameItem
import androidx.compose.ui.graphics.Color
import org.koin.compose.viewmodel.koinViewModel




@Composable
fun FavoriteScreen(modifier: Modifier = Modifier,
                   onBackClick:() -> Unit,
                   onDetails: (Int) -> Unit
                   ){
   val viewModel = koinViewModel<FavoriteViewModel>()
    val favoriteGames = viewModel.favoriteGames.collectAsStateWithLifecycle()
    val downloadedGames = viewModel.downloadedGames.collectAsStateWithLifecycle()
    val reviewedGames = viewModel.reviewedGames.collectAsStateWithLifecycle()

    var playingGameName by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        FavoriteScreenContent(
            modifier = modifier.fillMaxSize(),
            favoriteGames = favoriteGames.value,
            downloadedGames = downloadedGames.value,
            reviewedGames = reviewedGames.value,
            onBackClick = onBackClick,
            onDetails = onDetails,
            onDelete = {
                viewModel.delete(it)
            },
            onPlay = { name -> playingGameName = name }
        )

        playingGameName?.let { name ->
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black).clickable { playingGameName = null },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Playing $name (Offline Mode)...", color = Color.White, style = MaterialTheme.typography.headlineLarge)
                    Spacer(Modifier.height(20.dp))
                    IconButton(onClick = { playingGameName = null }, modifier = Modifier.background(Color.White, CircleShape)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreenContent(modifier: Modifier = Modifier,
                          favoriteGames: List<Game>,
                          downloadedGames: List<Game>,
                          reviewedGames: List<Game>,
                          onBackClick:() -> Unit,
                          onDetails:(Int) -> Unit,
                          onDelete:(Int) -> Unit,
                          onPlay:(String) -> Unit
                          ){
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Favorites", "Downloads", "Reviews")

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column {
            TopAppBar(title ={
                Text("My Library")
            }, navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
                    modifier = Modifier.clickable{onBackClick()})
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
            TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color.White) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
        }
    }) { paddingValues ->
        val games = when(selectedTabIndex) {
            0 -> favoriteGames
            1 -> downloadedGames
            else -> reviewedGames
        }
        if (games.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No games found...")
            }

       }else{
            LazyColumn(
                modifier = Modifier.padding(paddingValues).fillMaxSize()
            ) {
                items(
                    items = games,
                    key = { it.id }
                ) { item ->
                    GameItem(
                        modifier = Modifier.fillMaxWidth(),
                        isDeleteShown = true,
                        item = item,
                        onClick = onDetails,
                        onDeleteClick = onDelete,
                        onPlayClick = { onPlay(item.name ?: "Unknown") }
                    )
                }
            }
       }
    }
}

