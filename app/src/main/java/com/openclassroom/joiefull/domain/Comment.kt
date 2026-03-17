package com.openclassroom.joiefull.domain

data class Comment (
    val idComment: Int,
    val idProduct: Int,
    val idUser: Int,
    val userName: String,
    val userProfilePicture: String?,
    val comment: String,
    val rate: Int,
)


