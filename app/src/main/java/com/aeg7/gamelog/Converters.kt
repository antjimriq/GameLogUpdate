package com.aeg7.gamelog

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun restoreList(listOfString: String?): List<String?>? {
        return Gson().fromJson(listOfString, object : TypeToken<List<String?>?>() {}.type)
    }

    @TypeConverter
    fun saveList(listOfString: List<String?>?): String? {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun fromUserPreferences(userPreferences: UserPreferences): List<String?>? {
        return listOf(
            userPreferences.ownership,
            userPreferences.physicalCopy,
            userPreferences.digitalCopy,
            userPreferences.collectors,
            userPreferences.extra,
            userPreferences.date.toString(),
            userPreferences.status.toString(),
            userPreferences.mark.toString(),
            userPreferences.console,
            userPreferences.comments
        )
    }

    @TypeConverter
    fun toUserPreferences(listOfString: List<String?>?): UserPreferences? {
        return listOfString?.get(0)?.let {
            listOfString[1]?.let { it1 ->
                listOfString[2]?.let { it2 ->
                    listOfString[3]?.let { it3 ->
                        listOfString[4]?.let { it4 ->
                            listOfString[5]?.let { it5 ->
                                listOfString[6]?.let { it6 ->
                                    listOfString[7]?.let { it7 ->
                                        listOfString[8]?.let { it8 ->
                                            listOfString[9]?.let { it9 ->
                                                UserPreferences(
                                                    it,
                                                    it1,
                                                    it2,
                                                    it3,
                                                    it4,
                                                    it5,
                                                    it6, it7, it8, it9
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}