package com.example.dictionary.data.room.converter_room

import androidx.room.TypeConverter
import com.example.dictionary.domain.entity.Translation
import com.google.gson.Gson

class MeaningsConverter {
    @TypeConverter
    fun listToJson(value: Translation): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Translation {
        return Gson().fromJson(value, Translation::class.java)
    }
}