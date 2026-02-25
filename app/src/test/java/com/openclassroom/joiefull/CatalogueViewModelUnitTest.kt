package com.openclassroom.joiefull

import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.screens.catalogueScreen.CatalogueViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CatalogueViewModelUnitTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CatalogueViewModel
    private lateinit var repository: CatalogueRepository

    val testProducts = listOf(
        // SHOES
        Product(
            id = 3,
            pictureUrl = "url_3",
            description = "Baskets blanches",
            name = "Baskets blanches",
            category = "SHOES",
            likes = 20,
            currentPrice = 89.99,
            originalPrice = 120.00,
            rate = 4.8
        ),

        // ACCESSORIES
        Product(
            id = 1,
            pictureUrl = "url_1",
            description = "Sac à main orange",
            name = "Sac orange",
            category = "ACCESSORIES",
            likes = 10,
            currentPrice = 69.99,
            originalPrice = 120.99,
            rate = 4.5
        ),

        // CLOTHING
        Product(
            id = 5,
            pictureUrl = "url_5",
            description = "T-shirt blanc coton",
            name = "T-shirt blanc",
            category = "CLOTHING",
            likes = 15,
            currentPrice = 19.99,
            originalPrice = 39.99,
            rate = 4.2
        ),

        // ACCESSORIES
        Product(
            id = 2,
            pictureUrl = "url_2",
            description = "Lunettes de soleil noires",
            name = "Lunettes noires",
            category = "ACCESSORIES",
            likes = 5,
            currentPrice = 29.99,
            originalPrice = 29.99,
            rate = null
        ),

        // SHOES
        Product(
            id = 4,
            pictureUrl = "url_4",
            description = "Chaussures de ville",
            name = "Derbies cuir",
            category = "SHOES",
            likes = 2,
            currentPrice = 99.99,
            originalPrice = 99.99,
            rate = 3.9
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun should_ReturnGroupedByCategoryList() = runTest {
        coEvery { repository.getCatalogue() } returns flowOf(testProducts)
        viewModel = CatalogueViewModel(repository)

        val job = launch { viewModel.catalogue.collect {} }

        advanceUntilIdle()

        val catalogue = viewModel.catalogue.value

        assert(catalogue.size == 3) // il y a 3 categorie
        assert(catalogue["SHOES"]?.size == 2) // il y a 2 shoes
        assert(catalogue["CLOTHING"]?.size == 1) // il y a 1 t-shirt
        assert(catalogue["ACCESSORIES"]?.size == 2) // il y a 2 sacs

        job.cancel()
    }


}




