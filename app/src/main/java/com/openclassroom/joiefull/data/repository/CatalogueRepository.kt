package com.openclassroom.joiefull.data.repository

import com.openclassroom.joiefull.data.remote.api.CatalogueApi
import com.openclassroom.joiefull.domain.Product
import javax.inject.Inject
import com.openclassroom.joiefull.data.mapper.toDomain
import com.openclassroom.joiefull.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

@Singleton
class CatalogueRepository @Inject constructor(private val catalogueApi: CatalogueApi) {

    private val _catalogueFlow = MutableStateFlow<List<Product>>(emptyList())

    private val likedProductIds = mutableSetOf<Int>(1, 4, 9)


    suspend fun loadCatalogue(): DataState<Unit> {
        return try {
            //Get likedProduct here and then load product when API allow it
            val products = catalogueApi.getCatalogue().map { it ->
                val product = it.toDomain()
                product.copy(isLikedByCurrentUser = likedProductIds.contains(product.id))
            }
            _catalogueFlow.value = products
            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error(e.message ?: "Network error")
        }
    }

    fun getCatalogue(): Flow<List<Product>> = _catalogueFlow.asStateFlow()

    fun getProduct(productId: Int): Flow<Product?>{
        return _catalogueFlow.asStateFlow().map { products ->
            products.find { it.id == productId }
        }
    }

    fun getCachedProduct(productId: Int): Product? {
        return _catalogueFlow.value.find { it.id == productId }
    }

    fun toggleLike(productId: Int) {
        val newLikedStatus = !likedProductIds.contains(productId)

        if (newLikedStatus) likedProductIds.add(productId)
        else likedProductIds.remove(productId)

        _catalogueFlow.update { products ->
            products.map { product ->
                if (product.id == productId) product.copy(
                    isLikedByCurrentUser = newLikedStatus,
                    likes = if (newLikedStatus) product.likes + 1 else product.likes - 1
                )
                else product
            }
        }
    }

}