package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Comment
import com.openclassroom.joiefull.domain.Product
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
class DetailViewModel @Inject constructor(private val repository: CatalogueRepository) : ViewModel() {

    private val _commentsUiState = MutableStateFlow<CommentUiState>(CommentUiState())
    val commentsUiState = _commentsUiState.asStateFlow()

    fun getProduct(productId: Int) : StateFlow<Product?> {
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

    fun loadComments (productId: Int) {
        viewModelScope.launch {
            _commentsUiState.update { it.copy(isLoading = true) }
            val comments = repository.getComments(productId)
            _commentsUiState.update { it.copy(comments = comments.reversed(), isLoading = false) }
        }
    }

    fun addComment(newComment: Comment) {
        viewModelScope.launch {
            repository.addComment(newComment)
            loadComments(newComment.idProduct)
        }
    }


}

data class CommentUiState(
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


