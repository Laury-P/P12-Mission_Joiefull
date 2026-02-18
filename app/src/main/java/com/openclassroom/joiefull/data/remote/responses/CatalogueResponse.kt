package com.openclassroom.joiefull.data.remote.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatalogueResponse (
    val id: Int,
    val picture: Picture,
    val name: String,
    val category: String,
    val likes: Int,
    @Json(name = "price")
    val currentPrice: Double,
    @Json(name = "original_price")
    val originalPrice: Double,
){
    @JsonClass(generateAdapter = true)
    data class Picture(
        val url: String,
        val description: String
    )
}
