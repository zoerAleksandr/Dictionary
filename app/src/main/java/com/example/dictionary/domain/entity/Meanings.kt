package com.example.dictionary.domain.entity

import com.google.gson.annotations.SerializedName

data class Meanings(
    @field:SerializedName("translation")
    val translation: Translation?,
    @field:SerializedName("previewUrl")
    val previewUrl: String?,
    @field:SerializedName("transcription")
    val transcription: String?,
    @field:SerializedName("soundUrl")
    val soundUrl: String?
)