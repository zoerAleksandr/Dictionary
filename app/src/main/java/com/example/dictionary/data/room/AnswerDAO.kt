package com.example.dictionary.data.room

import androidx.room.*
import com.example.dictionary.data.room.entity.AnswerDTO
import com.example.dictionary.data.room.entity.AnswerWithMeanings
import com.example.dictionary.data.room.entity.MeaningsDTO
import com.example.dictionary.domain.entity.Meanings

@Dao
interface AnswerDAO {
    @Query("SELECT * FROM meanings")
    fun getAllData(): List<Meanings>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnswer(answer: AnswerDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeanings(meaningsDTO: MeaningsDTO): Long

    @Transaction
    fun insertAnswerWithMeanings(answerWithMeanings: AnswerWithMeanings) {
        insertAnswer(answerWithMeanings.answerDTO)
        for (meanings in answerWithMeanings.listMeanings) {
            insertMeanings(meanings)
        }
    }

    @Transaction
    @Query("SELECT * FROM answer WHERE text LIKE :answerText")
    fun getAnswerWithMeanings(answerText: String): AnswerWithMeanings

    @Query("SELECT * FROM meanings WHERE answerText LIKE '%' || :answerText || '%'")
    fun getMeaningsByAnswerText(answerText: String): List<Meanings>

    @Query("SELECT * FROM meanings WHERE answerText LIKE '%' || :answerText || '%'" +
            " AND isFavorite LIKE :isFavorite")
    fun getFavoriteMeaningsByAnswerText(answerText: String, isFavorite: Int): List<Meanings>

    @Query("SELECT * FROM meanings WHERE isFavorite LIKE :isFavorite")
    fun getAllFavoritesMeanings(isFavorite: Int): List<Meanings>

    @Update(entity = MeaningsDTO::class)
    fun updateMeanings(meanings: Meanings)
}