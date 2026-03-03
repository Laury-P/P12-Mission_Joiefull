package com.openclassroom.joiefull.ui.screens.detailScreen

import androidx.lifecycle.ViewModel
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: CatalogueRepository) : ViewModel() {

    fun getProduct(productId: Int) : Product? = repository.getProduct(productId)

}



