package com.lidigu.coreNetwork.apiService

import com.lidigu.coreNetwork.model.game.FreeToGameResult
import com.lidigu.coreNetwork.model.game.GameResponse
import com.lidigu.coreNetwork.model.gameDetails.GameDetailsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    val httpClient: HttpClient
) {
    suspend fun getGames(): Result<GameResponse>{
      return  try {
            // FreeToGame API returns an array directly, so we wrap it
            val response = httpClient.get("/api/games").body<List<FreeToGameResult>>()
            Result.success(GameResponse(games = response))
        } catch (e: Exception) {
            Result.failure(Exception("GetGames failed: ${e.message}", e))
        }
    }

    suspend fun search(q: String): Result<GameResponse>{
        // FreeToGame doesn't have a search endpoint, so we filter client-side
        return try {
            val response = httpClient.get("/api/games").body<List<FreeToGameResult>>()
            val filtered = response.filter { game ->
                game.title?.contains(q, ignoreCase = true) == true ||
                game.short_description?.contains(q, ignoreCase = true) == true
            }
            Result.success(GameResponse(games = filtered))
        } catch (e: Exception) {
            Result.failure(Exception("Search failed: ${e.message}", e))
        }
    }

    suspend fun getDetails(id:Int): Result<GameDetailsResponse>{
        return try {
            val response = httpClient.get("/api/game"){
                parameter("id", id)
            }.body<GameDetailsResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(Exception("GetDetails failed for id $id: ${e.message}", e))
        }

    }

}