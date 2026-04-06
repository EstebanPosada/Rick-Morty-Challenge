package com.estebanposada.rickmorty_app.ui.screens.list

import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.GetCharactersUseCase
import com.estebanposada.rickmorty_app.MainDispatcherRule
import com.estebanposada.rickmorty_app.character
import com.estebanposada.rickmorty_app.ui.screens.common.toUi
import com.estebanposada.rickmorty_app.ui.screens.common.toUserMessage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp() {
        getCharactersUseCase = mockk<GetCharactersUseCase>(relaxed = true)
    }

    @Test
    fun `when loadInitial is called, then getCharactersUseCase fails`() = runTest {
        val page = 1
        val error = Resource.Error(AppError.Network)

        coEvery { getCharactersUseCase(page) } returns error

        viewModel = CharacterListViewModel(getCharactersUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(AppError.Network.toUserMessage(), state.error)
    }

    @Test
    fun `when loadInitial is called, then getCharactersUseCase succeeds`() = runTest {
        val page = 1
        val result = Resource.Success(listOf(character))
        val expected = result.data.map { it.toUi() }

        coEvery { getCharactersUseCase(page) } returns result

        viewModel = CharacterListViewModel(getCharactersUseCase)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(expected, state.data)
    }


    @Test
    fun `when loadNextPage is called, then getCharactersUseCase fails`() = runTest {
        val page = 1
        val error = Resource.Error(AppError.Network)

        coEvery { getCharactersUseCase(page) } returns error

        viewModel = CharacterListViewModel(getCharactersUseCase)
        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(AppError.Network.toUserMessage(), state.error)
    }

    @Test
    fun `when loadNextPage is called, then getCharactersUseCase succeeds and appends new data`() = runTest {
        val firstResult = Resource.Success(listOf(character))
        val secondResult = Resource.Success(listOf(character.copy(id = "2", name = "second")))
        val expected = (firstResult.data + secondResult.data).map { it.toUi() }

        coEvery { getCharactersUseCase(1) } returns firstResult
        coEvery { getCharactersUseCase(2) } returns secondResult

        viewModel = CharacterListViewModel(getCharactersUseCase)
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(false, state.isLoadingMore)
        assertEquals(expected, state.data)
        coVerify(exactly = 2) { getCharactersUseCase(any()) }
    }


    @Test
    fun `when onRetry is called, then getCharactersUseCase succeeds`() = runTest {
        val page = 1
        val error = Resource.Error(AppError.Network)
        val result = Resource.Success(listOf(character))
        val expected = result.data.map { it.toUi() }

        coEvery { getCharactersUseCase(page) } returns error
        coEvery { getCharactersUseCase(page) } returns result

        viewModel = CharacterListViewModel(getCharactersUseCase)
        advanceUntilIdle()

        viewModel.onRetry()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(expected, state.data)
    }
}
