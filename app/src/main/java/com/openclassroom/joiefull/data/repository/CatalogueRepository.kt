package com.openclassroom.joiefull.data.repository

import com.openclassroom.joiefull.data.remote.api.CatalogueApi
import com.openclassroom.joiefull.domain.Product
import javax.inject.Inject
import com.openclassroom.joiefull.data.mapper.toDomain


class CatalogueRepository @Inject constructor(private val catalogueApi: CatalogueApi){

    suspend fun getCatalogue(): List<Product> {
        return catalogueApi.getCatalogue().map { it.toDomain() }
    }






}