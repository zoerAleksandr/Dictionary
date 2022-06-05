package com.example.dictionary.domain.entity

data class Request(
    val text: String,
    val meanings: List<Meanings>?
)