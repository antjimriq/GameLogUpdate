package com.aeg7.gamelog

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "games")
data class Game(@PrimaryKey val id: Long, val name: String, val logo:String?, val platform: List<String>, var myGame:Boolean=false, var preferences: UserPreferences = UserPreferences()):Parcelable {
}