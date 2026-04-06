package com.estebanposada.rickmorty_app.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.GetCharacterDetailUseCase
import com.estebanposada.rickmorty_app.MainDispatcherRule
import com.estebanposada.rickmorty_app.character
import com.estebanposada.rickmorty_app.ui.screens.common.toDetailUi
import com.estebanposada.rickmorty_app.ui.screens.common.toUserMessage
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    @get:Rule
    private val dispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        getCharacterDetailUseCase = mockk<GetCharacterDetailUseCase>()
        savedStateHandle =
            SavedStateHandle(mapOf(CharacterDetailViewModel.CHARACTER_ID to character.id))
    }

    @Test
    fun `when loadCharacterInfo is called, then getCharacterDetailUseCase fails`() = runTest {
        val id = character.id
        val appError = AppError.Network
        val messageError = appError.toUserMessage()

        coEvery { getCharacterDetailUseCase(id) } returns Resource.Error(appError)
        savedStateHandle =
            SavedStateHandle(mapOf(CharacterDetailViewModel.CHARACTER_ID to character.id))

        val states = mutableListOf<CharacterDetailState>()
        val job = launch { viewModel.state.collect { states.add(it) } }
        viewModel = CharacterDetailViewModel(getCharacterDetailUseCase, savedStateHandle)
        advanceUntilIdle()

        val lastState = states.last()
        assertEquals(CharacterDetailState.Error(messageError), lastState)
        job.cancel()
    }

    @Test
    fun `when loadCharacterInfo is called, then getCharacterDetailUseCase succeeds`() = runTest {
        val id = character.id
        val expected = character.toDetailUi()

        coEvery { getCharacterDetailUseCase(id) } returns Resource.Success(character)

        viewModel = CharacterDetailViewModel(getCharacterDetailUseCase, savedStateHandle)
        advanceUntilIdle()

        val states = mutableListOf<CharacterDetailState>()
        val job = launch { viewModel.state.collect { states.add(it) } }
        advanceUntilIdle()

        val lastState = states.last()
        assertEquals(CharacterDetailState.Success(expected), lastState)
        job.cancel()
    }


    @Test
    fun `when onRetry is called, then getCharacterDetailUseCase succeeds`() = runTest {
        val id = character.id
        val appError = AppError.Network
        val expected = character.toDetailUi()

        coEvery { getCharacterDetailUseCase(id) } returns Resource.Error(appError)

        viewModel = CharacterDetailViewModel(getCharacterDetailUseCase, savedStateHandle)
        advanceUntilIdle()
        coEvery { getCharacterDetailUseCase(id) } returns Resource.Success(character)

        viewModel.onRetry()
        val states = mutableListOf<CharacterDetailState>()
        val job = launch { viewModel.state.collect { states.add(it) } }
        advanceUntilIdle()

        val lastState = states.last()
        assertEquals(CharacterDetailState.Success(expected), lastState)
        job.cancel()
    }

}
