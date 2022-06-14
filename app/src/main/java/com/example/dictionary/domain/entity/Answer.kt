package com.example.dictionary.domain.entity

data class Answer(
    val id: Long = 0,
    val text: String,
    val meanings: List<Meanings>?
)