package com.example.dictionary.data.room.converter_room

import androidx.room.TypeConverter
import com.example.dictionary.domain.entity.Meanings
import com.google.gson.Gson

class AnswerConverter {
    @TypeConverter
    fun listToJson(value: List<Meanings>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<Meanings>::class.java).toList()
}