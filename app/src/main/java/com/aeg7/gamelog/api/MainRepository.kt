package com.aeg7.gamelog.api

import android.app.Application
import android.util.Log
import com.aeg7.gamelog.Game
import com.aeg7.gamelog.database.GamesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
private val key = "7e8c32c7ac3140cd94a143252b925b94"
private val page_size="5"
class MainRepository(private val database:GamesDatabase, val application: Application) {

    suspend fun importGames():MutableList<Game>{
        return withContext(Dispatchers.IO){
            for (page in 1..40) {
                val gamesJsonResponse = service.listGames(key,page_size,page.toString())
                Log.d("List_of_Games", gamesJsonResponse.toString())
                val listGames = parseResult(gamesJsonResponse)
                database.gameDao.insertAll(listGames)
            }
            println(database.gameDao.getGames().toString())
            database.gameDao.getGames()
        }
    }

    suspend fun gamesfromDB(): MutableList<Game> {
        return withContext(Dispatchers.IO){
        database.gameDao.getGames()
        }
    }

    suspend fun selectGames(): MutableList<Game> {
        return withContext(Dispatchers.IO){
            val gameList = database.gameDao.getGames()
            gameList
        }
    }

    suspend fun selectMyGames(): MutableList<Game> {
        return withContext(Dispatchers.IO){
            val gameList = database.gameDao.getMyGames()
            gameList
        }
    }


    suspend fun updateGames(gameList: MutableList<Game>){
        return withContext(Dispatchers.IO){
            for (game in gameList){
                database.gameDao.updateGame(game)
            }
        }
    }

    suspend fun updateOneGame(game: Game){
        return withContext(Dispatchers.IO){
                database.gameDao.updateGame(game)
        }
    }

    suspend fun db_exits():Boolean{
        return withContext(Dispatchers.IO){
            database.gameDao.dbExists()>0
        }
    }

    private fun parseResult(gamesJsonResponse: GamesJsonResponse):MutableList<Game>{

        val gameList= mutableListOf<Game>()
        val results = gamesJsonResponse.results

        for (result in results){
            val id= result.id
            val name= result.name
            val plataformas= mutableListOf<String>()
            for (plataforma in result.platforms){
                plataformas.add(plataforma.platform.name)
            }
            gameList.add(Game(id,name,result.background_image,plataformas))
        }

        return gameList
    }
}