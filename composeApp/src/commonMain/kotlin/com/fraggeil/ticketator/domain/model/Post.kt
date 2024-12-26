package com.fraggeil.ticketator.domain.model

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val imageUrl: String,
    val date: String
)
