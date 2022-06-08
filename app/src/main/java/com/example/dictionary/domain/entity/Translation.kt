package com.example.dictionary.domain.entity

import com.google.gson.annotations.SerializedName

private const val SERIALIZED_TRANSLATION = "text"

data class Translation(
    @field:SerializedName(SERIALIZED_TRANSLATION)
    val translation: String
)
