package com.openclassroom.joiefull.data.remote.api

import com.openclassroom.joiefull.data.remote.responses.CatalogueResponse
import retrofit2.http.GET

interface CatalogueApi {

    @GET("clothes.json")
    suspend fun getCatalogue(): List<CatalogueResponse>


}