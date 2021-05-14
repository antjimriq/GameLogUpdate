package com.aeg7.gamelog.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aeg7.gamelog.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(GameList: MutableList<Game>)

    @Query("SELECT * FROM games")
    fun getGames(): MutableList<Game>

    @Update
    fun updateGame(game: Game)

    @Query("SELECT * FROM games WHERE myGame = 1")
    fun getMyGames():MutableList<Game>

    @Query("SELECT COUNT(*) FROM games")
    fun dbExists():Int
}