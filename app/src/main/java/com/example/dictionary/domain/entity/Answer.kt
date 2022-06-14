package com.example.dictionary.domain.entity

data class Answer(
    val text: String,
    val meanings: List<Meanings>?
)