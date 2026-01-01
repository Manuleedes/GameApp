package com.lidigu.coreNetwork.client


import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    //https://api.rawg.io/api/platforms?key=a558f7f840db4f79a9ccc0700d88f3bd
    fun getInstance(): HttpClient = HttpClient {
        install(ContentNegotiation){
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest){
            url{
                host = "api.rawg.io"
                protocol = URLProtocol.HTTPS
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
        install(HttpTimeout){
            socketTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            requestTimeoutMillis = 30000
        }
    }
}