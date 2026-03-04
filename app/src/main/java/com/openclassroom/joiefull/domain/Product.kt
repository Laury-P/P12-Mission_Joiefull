package com.openclassroom.joiefull.domain

data class Product (
    val id: Int,
    val pictureUrl: String,
    val description: String,
    val name: String,
    val category: String,
    val likes: Int = 0,
    val isLikedByCurrentUser: Boolean = false,
    val currentPrice: Double,
    val originalPrice: Double,
    val rate: Double? = null,
)