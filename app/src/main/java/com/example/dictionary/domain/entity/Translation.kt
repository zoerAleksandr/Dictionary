package com.example.dictionary.domain.entity

import com.google.gson.annotations.SerializedName

data class Translation(
    @field:SerializedName("text")
    val translation: String
)
