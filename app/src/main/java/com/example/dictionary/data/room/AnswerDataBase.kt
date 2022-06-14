package com.example.dictionary.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionary.data.room.converter_room.AnswerConverter
import com.example.dictionary.data.room.entity.AnswerDTO

@Database(entities = [AnswerDTO::class], version = 1, exportSchema = false)
@TypeConverters(AnswerConverter::class)
abstract class AnswerDataBase : RoomDatabase() {
    abstract fun answerDao(): AnswerDAO
}