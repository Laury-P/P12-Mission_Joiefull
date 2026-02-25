package com.openclassroom.joiefull.ui.screens.catalogueScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatalogueViewModel @Inject constructor(
    repository: CatalogueRepository
) : ViewModel() {

    val catalogue: StateFlow<Map<String, List<Product>>> = repository.getCatalogue()
        .map { products ->
            products.groupBy { it.category }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    fun onLikeClick(product: Product) {
        //TODO
    }

}