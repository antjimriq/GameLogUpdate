package com.aeg7.gamelog.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//7e8c32c7ac3140cd94a143252b925b94
interface ApiService {
    @GET("api/games")
    suspend fun listGames(@Query("key") key: String,@Query("page_size")page_size:String,@Query("page")page:String): GamesJsonResponse
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://api.rawg.io/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: ApiService = retrofit.create<ApiService>(
    ApiService::class.java)