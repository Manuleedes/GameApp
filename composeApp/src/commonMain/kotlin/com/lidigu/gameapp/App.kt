package com.lidigu.gameapp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lidigu.gameapp.navigation.FavoriteNavGraph
import com.lidigu.gameapp.navigation.GameNavGraph
import com.lidigu.gameapp.navigation.SearchNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navHostController = rememberNavController()
        val bottomPadding = WindowInsets.statusBars.asPaddingValues().calculateBottomPadding()
        NavHost(navHostController, startDestination = GameNavGraph.Dest.Root.route){
            listOf(
                GameNavGraph,
                SearchNavGraph,
                FavoriteNavGraph
            ).forEach {
                it.build(
                    modifier = Modifier.padding(top = bottomPadding).fillMaxSize(),
                    navHostController = navHostController,
                    navGraphBuilder = this
                )
            }

        }
    }
}