package com.example.dictionary.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.room.entity.AnswerDTO
import com.example.dictionary.domain.entity.Answer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface AnswerDAO {
    @Query("SELECT * FROM answer WHERE text =:answerText")
    fun getMeaningsListByAnswer(answerText: String): Observable<List<Answer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAnswer(answer: AnswerDTO): Single<Long>
}