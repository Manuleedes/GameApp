package com.lidigu.favorite.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lidigu.common.domain.model.Game
import com.lidigu.common.ui.listItem.GameItem
import org.jetbrains.skia.Color
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun FavoriteScreen(modifier: Modifier = Modifier,
                   onBackClick:() -> Unit,
                   onDetails: (Int) -> Unit
                   ){
   val viewModel = koinViewModel<FavoriteViewModel>()
    val games = viewModel.games.collectAsStateWithLifecycle()
    
    FavoriteScreenContent(
        modifier = modifier.fillMaxSize(),
        games = games.value,
        onBackClick = onBackClick,
        onDetails = onDetails,
        onDelete = {
            viewModel.delete(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreenContent(modifier: Modifier = Modifier,
                          games: List<Game>,
                          onBackClick:() -> Unit,
                          onDetails:(Int) -> Unit,
                          onDelete:(Int) -> Unit
                          ){
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title ={
            Text("Favorites:")
        }, navigationIcon = {
            Icon(imageVector =Icons.Default.ArrowBack, contentDescription = null,
                modifier = Modifier.clickable{onBackClick()})
        },contentColor = Color.BLACK,
            backgroundColor = Color.WHITE)
    }) {
       if (games.isEmpty()){
           Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               Text("No games found...")
           }

       }else{
           LazyColumn(modifier  = Modifier.fillMaxSize()) {
               items(games){ item ->
                   GameItem(
                       modifier = Modifier.fillMaxSize(),
                       isDeleteShown = true,
                       item = item,
                       onClick = onDetails,
                       onDeleteClick = onDelete,
                   )
               }

           }
       }
    }
}

