package com.example.news

data class MyData(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)