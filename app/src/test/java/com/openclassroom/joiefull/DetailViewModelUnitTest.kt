package com.openclassroom.joiefull

import app.cash.turbine.test
import com.openclassroom.joiefull.data.repository.CatalogueRepository
import com.openclassroom.joiefull.domain.Comment
import com.openclassroom.joiefull.domain.Product
import com.openclassroom.joiefull.ui.screens.detailScreen.DetailViewModel
import com.openclassroom.joiefull.util.DataState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelUnitTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: CatalogueRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        coEvery { repository.loadCatalogue() } returns DataState.Success(Unit)
        coEvery { repository.catalogueState } returns MutableStateFlow(DataState.Success(Unit))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun getProduct_should_ReturnProductWithGivenId_when_ProductExists() = runTest {
        val cachedProduct = Product(
            id = 2,
            pictureUrl = "url_2",
            description = "Lunettes de soleil noires",
            name = "Lunettes noires",
            category = "ACCESSORIES",
            likes = 3, //ancien nombres de like
            currentPrice = 29.99,
            originalPrice = 29.99,
            rate = null
        )
        val expectedProduct = Product(
            id = 2,
            pictureUrl = "url_2",
            description = "Lunettes de soleil noires",
            name = "Lunettes noires",
            category = "ACCESSORIES",
            likes = 5,//nouveau nombres de like
            currentPrice = 29.99,
            originalPrice = 29.99,
            rate = null
        )
        coEvery { repository.getProduct(2) } returns flowOf(expectedProduct)
        every { repository.getCachedProduct(2) } returns cachedProduct
        viewModel = DetailViewModel(repository)

        viewModel.getProduct(2).test{
            assertEquals(cachedProduct, awaitItem())
            assertEquals(expectedProduct, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getProduct_should_ReturnNull_when_ProductDoesNotExist() = runTest {
        coEvery { repository.getProduct(99) } returns flowOf(null)
        every { repository.getCachedProduct(99) } returns null
        viewModel = DetailViewModel(repository)

        viewModel.getProduct(99).test {
            assertEquals(null, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun loadComments_should_UpdateCommentsState() = runTest {
        coEvery { repository.getComments(1) } returns listOf(
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
                1,
                52,
                "Chloé Leroy",
                "https://api.dicebear.com/7.x/avataaars/png?seed=Chloe",
                "Livraison ultra rapide ! Le produit était super bien emballé.",
                5
            ),
        )

        viewModel = DetailViewModel(repository)

        viewModel.commentUiState.test {
            assertEquals(false, awaitItem().isLoading)

            viewModel.loadComments(1)

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            assertEquals(0, loadingState.comments.size)

            val commentsState = awaitItem()
            assertEquals(false, commentsState.isLoading)
            assertEquals(3, commentsState.comments.size)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addComment_should_refreshList() = runTest{
        val newComment = Comment(
                1,
                1,
                50,
                "Alice Martin",
                "https://api.dicebear.com/7.x/avataaars/png?seed=Alice",
                "Franchement, la qualité est incroyable pour le prix. Je ne regrette pas mon achat !",
                5
            )

        coEvery { repository.addComment(any()) } returns Unit
        coEvery { repository.getComments(1) } returns listOf(newComment)

        viewModel = DetailViewModel(repository)
        viewModel.addComment(newComment)

        advanceUntilIdle()

        val state = viewModel.commentUiState.value
        assertEquals(1, state.comments.size)
        assertEquals(newComment, state.comments[0])

    }

}