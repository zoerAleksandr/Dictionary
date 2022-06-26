package com.example.dictionary.domain.entity

import com.google.gson.annotations.SerializedName

private const val SERIALIZED_TRANSLATION = "translation"
private const val SERIALIZED_PREVIEW_URL = "previewUrl"
private const val SERIALIZED_TRANSCRIPTION = "transcription"
private const val SERIALIZED_SOUND_URL = "soundUrl"

data class Meanings(
    val id: Long,
    @field:SerializedName(SERIALIZED_TRANSLATION)
    val translation: Translation?,
    @field:SerializedName(SERIALIZED_PREVIEW_URL)
    val previewUrl: String?,
    @field:SerializedName(SERIALIZED_TRANSCRIPTION)
    val transcription: String?,
    @field:SerializedName(SERIALIZED_SOUND_URL)
    val soundUrl: String?
)