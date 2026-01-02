package com.lidigu.coreNetwork.apiService

import com.lidigu.coreNetwork.model.game.GameResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    val httpClient: HttpClient
) {
    //https://api.rawg.io/api/platforms?key=a558f7f840db4f79a9ccc0700d88f3bd
    suspend fun getGames(): Result<GameResponse>{
      return  try {
            val response = httpClient.get ( "api/games"){
                url{
                    parameter("key","a558f7f840db4f79a9ccc0700d88f3bd")
                }
            }.body<GameResponse>()
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun search(q: String): Result<GameResponse>{
        return  try {
            val response = httpClient.get ( "api/games"){
                url{
                    parameter("key","a558f7f840db4f79a9ccc0700d88f3bd")
                    parameter("search", q)
                }
            }.body<GameResponse>()
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}