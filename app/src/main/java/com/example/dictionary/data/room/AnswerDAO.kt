package com.example.dictionary.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.room.entity.AnswerDTO
import com.example.dictionary.domain.entity.Answer

@Dao
interface AnswerDAO {
    @Query("SELECT * FROM answer WHERE text =:answerText")
    fun getMeaningsListByAnswerAsync(answerText: String): List<Answer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAnswerAsync(answer: AnswerDTO): Long
}