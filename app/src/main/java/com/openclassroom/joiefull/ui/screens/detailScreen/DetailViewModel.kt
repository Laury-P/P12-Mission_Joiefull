package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Comment
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: CatalogueRepository) :
    ViewModel() {

    init {
        viewModelScope.launch {
            repository.loadCatalogue()
        }
    }

    val isCatalogueReady: StateFlow<DataState<Unit>> =
        repository.catalogueState as StateFlow<DataState<Unit>>

    private val _commentUiState = MutableStateFlow(CommentsUiState())
    val commentUiState = _commentUiState.asStateFlow()

    fun getProduct(productId: Int): StateFlow<Product?> {
        return repository.getProduct(productId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                repository.getCachedProduct(productId)
            )
    }

    fun onLikeClick(product: Product) {
        repository.toggleLike(product.id)
    }

    fun loadComments(productId: Int) {
        viewModelScope.launch {
            _commentUiState.update { it.copy(isLoading = true) }
            val comments = repository.getComments(productId)
            _commentUiState.update { it.copy(comments = comments.reversed(), isLoading = false) }
        }
    }

    fun addComment(newComment: Comment) {
        viewModelScope.launch {
            repository.addComment(newComment)
            loadComments(newComment.idProduct)
        }
    }


}

data class CommentsUiState(
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)


