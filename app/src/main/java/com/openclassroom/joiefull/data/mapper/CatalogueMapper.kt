package com.openclassroom.joiefull.data.mapper

import com.openclassroom.joiefull.data.remote.responses.CatalogueResponse
import com.openclassroom.joiefull.domain.Product


fun Product.toCatalogueResponse(): CatalogueResponse {
    return CatalogueResponse(
        id = id,
        picture = CatalogueResponse.Picture(pictureUrl, description),
        name = name,
        category = category,
        likes = likes,
        currentPrice = currentPrice,
        originalPrice = originalPrice,
    )
}

fun CatalogueResponse.toDomain(): Product {
    return Product(
        id = id,
        pictureUrl = picture.url,
        description = picture.description,
        name = name,
        category = category,
        likes = likes,
        currentPrice = currentPrice,
        originalPrice = originalPrice,
    )
}
