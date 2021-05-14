package com.aeg7.gamelog

import android.app.Application
import androidx.lifecycle.*
import com.aeg7.gamelog.api.MainRepository
import com.aeg7.gamelog.database.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(app: Application) :AndroidViewModel(app) {
    private val database= getDatabase(app)
    private val repository= MainRepository(database, app)
    private var _gamesList = MutableLiveData<MutableList<Game>>()
    val gamesList:LiveData<MutableList<Game>>
    get() = _gamesList
    init {
        viewModelScope.launch {
            if (repository.db_exits()){
                _gamesList.value=repository.gamesfromDB()
            }else{
                _gamesList.value=repository.importGames()
            }
        }
    }
}