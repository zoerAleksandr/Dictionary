package com.example.dictionary.data.room

import com.example.dictionary.data.room.entity.AnswerWithMeanings
import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.domain.repository.IRepository
import com.example.dictionary.view.favorite_screen.IS_FAVORITE

class RepositoryRoomImpl(private val dao: AnswerDAO) : IRepository<List<Meanings>> {

    fun updateMeanings(meanings: Meanings){
        dao.updateMeanings(meanings)
    }

    fun getAllFavoritesMeanings(isFavorite: Int): List<Meanings>{
        return dao.getAllFavoritesMeanings(isFavorite)
    }

    fun getAllData(): List<Meanings> {
        return dao.getAllData()
    }

    override suspend fun getData(text: String): List<Meanings> {
        return dao.getMeaningsByAnswerText(text)
    }

    fun getFavoriteMeaningsByAnswerText(text: String): List<Meanings>{
        return dao.getFavoriteMeaningsByAnswerText(text, IS_FAVORITE)
    }

    fun insertAnswerWithMeanings(answerWithMeanings: AnswerWithMeanings) {
        dao.insertAnswerWithMeanings(answerWithMeanings)
    }
}