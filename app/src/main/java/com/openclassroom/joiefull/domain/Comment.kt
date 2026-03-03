package com.openclassroom.joiefull.domain

data class Comment (
    val idProduct: Int,
    val idUser: Int,
    val comment: String,
    val rate: Int,
)


