package com.example.dictionary.view

import com.example.dictionary.domain.entity.Meanings
import com.example.dictionary.view.favorite_screen.IS_FAVORITE
import com.example.dictionary.view.favorite_screen.IS_NOT_FAVORITE
import kotlin.reflect.KProperty

class DelegateChangeFavoriteStatus(m: Meanings) {
    var meanings: Meanings = m

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Meanings {
        val data = meanings
        when (meanings.isFavorite) {
            IS_NOT_FAVORITE -> {
                data.isFavorite = IS_FAVORITE
            }
            IS_FAVORITE -> {
                data.isFavorite = IS_NOT_FAVORITE
            }
        }
        return data
    }
}

fun delegateUser(m: Meanings): DelegateChangeFavoriteStatus {
    return DelegateChangeFavoriteStatus(m)
}