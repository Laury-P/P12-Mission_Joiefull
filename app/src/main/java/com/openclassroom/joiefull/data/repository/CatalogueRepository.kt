package com.openclassroom.joiefull.data.repository

import com.openclassroom.joiefull.data.remote.api.CatalogueApi
import com.openclassroom.joiefull.domain.Product
import javax.inject.Inject
import com.openclassroom.joiefull.data.mapper.toDomain
import com.openclassroom.joiefull.domain.Comment
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

    private val _catalogueState = MutableStateFlow<DataState<Unit>>(DataState.Loading)
    val catalogueState: Flow<DataState<Unit>> = _catalogueState.asStateFlow()


    private val likedProductIds = mutableSetOf(1, 4, 9)

    private val allComments = mutableListOf(
        Comment(
            1,
            1,
            50,
            "Alice Martin",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Alice",
            "Franchement, la qualité est incroyable pour le prix. Je ne regrette pas mon achat !",
            5
        ),
        Comment(
            2,
            1,
            51,
            "Jean Dupont",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Jean",
            "Un peu déçu par la couleur, c'est plus foncé que sur la photo.",
            3
        ),
        Comment(
            3,
            4,
            52,
            "Chloé Leroy",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Chloe",
            "Livraison ultra rapide ! Le produit était super bien emballé.",
            5
        ),
        Comment(
            4,
            1,
            53,
            "Thomas Bernard",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Thomas",
            "Ça fait le job, mais les finitions pourraient être meilleures au niveau des coutures.",
            3
        ),
        Comment(
            5,
            1,
            54,
            "Sarah Petit",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Sarah",
            "J'adore ! C'est exactement ce que je cherchais depuis des mois.",
            5
        ),
        Comment(
            6,
            6,
            55,
            "Lucas Morel",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Lucas",
            "Attention, ça taille assez petit. Prenez une taille au-dessus.",
            4
        ),
        Comment(
            7,
            4,
            56,
            "Emma Roux",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Emma",
            "Le service client est top, ils ont répondu à toutes mes questions avant l'achat.",
            5
        ),
        Comment(
            8,
            6,
            57,
            "Nicolas Simon",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Nicolas",
            "Moyen. Je m'attendais à quelque chose de plus robuste.",
            2
        ),
        Comment(
            9,
            1,
            58,
            "Julie Michel",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Julie",
            "Parfait pour offrir en cadeau, la boîte est très élégante.",
            5
        ),
        Comment(
            10,
            4,
            59,
            "Damien Lefebvre",
            "https://api.dicebear.com/7.x/avataaars/png?seed=Damien",
            "Rapport qualité-prix correct, sans plus.",
            3
        )
    ) //Mock de la BDD


    suspend fun loadCatalogue(): DataState<Unit> {
        if (_catalogueState.value is DataState.Success) {
            return DataState.Success(Unit)
        }

        _catalogueState.value = DataState.Loading

        return try {
            val products = catalogueApi.getCatalogue().map { it ->
                val product = it.toDomain()
                val averageRate = allComments
                    .filter { it.idProduct == product.id }
                    .map { it.rate }
                    .average()
                    .let { if (it.isNaN()) 0.0 else it }
                product.copy(
                    isLikedByCurrentUser = likedProductIds.contains(product.id),
                    rate = averageRate
                )
            }
            _catalogueFlow.value = products
            _catalogueState.value = DataState.Success(Unit)
            DataState.Success(Unit)
        } catch (e: Exception) {
            _catalogueState.value = DataState.Error(e.message ?: "Network error")
            DataState.Error(e.message ?: "Network error")
        }
    }

    fun getCatalogue(): Flow<List<Product>> = _catalogueFlow.asStateFlow()

    fun getProduct(productId: Int): Flow<Product?> {
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


    suspend fun getComments(productId: Int): List<Comment> {
        return allComments.filter { it.idProduct == productId }
    }

    suspend fun addComment(newComment: Comment) {
        allComments.add(newComment)
        _catalogueFlow.update { products ->
            products.map { product ->
                if (product.id == newComment.idProduct) product.copy(
                    rate = allComments.filter { it.idProduct == product.id }.map { it.rate }
                        .average()
                )
                else product
            }
        }
    }

}