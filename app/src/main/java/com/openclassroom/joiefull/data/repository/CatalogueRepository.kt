package com.openclassroom.joiefull.data.repository

import com.openclassroom.joiefull.data.remote.api.CatalogueApi
import com.openclassroom.joiefull.domain.Product
import javax.inject.Inject
import com.openclassroom.joiefull.data.mapper.toDomain
import com.openclassroom.joiefull.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Singleton

@Singleton
class CatalogueRepository @Inject constructor(private val catalogueApi: CatalogueApi){

    private val _catalogueFlow = MutableStateFlow<List<Product>>(emptyList())

    suspend fun loadCatalogue() : DataState<Unit> {
        return try{
            val products = catalogueApi.getCatalogue().map { it.toDomain() }
            _catalogueFlow.value = products
            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error(e.message ?: "Network error")
        }
    }

    fun getCatalogue() : Flow<List<Product>> = _catalogueFlow.asStateFlow()




}