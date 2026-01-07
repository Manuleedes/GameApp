package com.lidigu.gameapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lidigu.game.ui.game.GameScreen
import com.lidigu.game.ui.gameDetails.GameDetailsScreen

object GameNavGraph : BaseNavGraph {


    sealed class Dest(val route: String) {
        data object Root : Dest("/game-root")
        data object Game : Dest("/game")

        data object Details : Dest("/game_details/{id}?previous={previous}") {
            fun getRoute(id: Int, previous: String = "game"): String {
                return "/game_details/$id?previous=$previous"
            }
        }
    }

    override fun build(
        modifier: Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            route = Dest.Root.route,
            startDestination = Dest.Game.route
        ) {
            composable(route = Dest.Game.route) {
                GameScreen(
                    modifier = modifier.fillMaxSize(),
                    onFavoriteClick = {
                        navHostController.navigate(
                            FavoriteNavGraph.Dest.Favorite.route
                        )
                    },
                    onSearchClick = {
                        navHostController.navigate(
                            SearchNavGraph.Dest.Search.route
                        )
                    },
                    onClick = { gameId ->
                        navHostController.navigate(
                            Dest.Details.getRoute(
                                id = gameId,
                                previous = "game"
                            )
                        )
                    }
                )
            }
            composable(route = Dest.Details.route) { backStackEntry ->

                val id = backStackEntry.arguments
                    ?.getString("id")
                    ?: return@composable

                val previous = backStackEntry.arguments
                    ?.getString("previous")
                    ?: "game"

                GameDetailsScreen(
                    modifier = modifier.fillMaxSize(),
                    id = id,
                    onBackClick = {
                        navHostController.popBackStack()
                    },
                    onDeleteSuccess = {
                        when (previous) {
                            "game" -> {
                                navHostController.popBackStack(
                                    route = Dest.Game.route,
                                    inclusive = false
                                )
                            }
                            "favorite" -> {
                                navHostController.popBackStack(
                                    route = FavoriteNavGraph.Dest.Favorite.route,
                                    inclusive = false
                                )
                            }
                            else -> navHostController.popBackStack()
                        }
                    }

                )
            }
        }
    }
}
