package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: CatalogueRepository) : ViewModel() {

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
}



