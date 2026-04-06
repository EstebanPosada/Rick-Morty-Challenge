package com.estebanposada.rickmorty_app.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.usecase.GetCharacterDetailUseCase
import com.estebanposada.rickmorty_app.ui.screens.common.onFailure
import com.estebanposada.rickmorty_app.ui.screens.common.onSuccess
import com.estebanposada.rickmorty_app.ui.screens.common.toDetailUi
import com.estebanposada.rickmorty_app.ui.screens.common.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val CHARACTER_ID = "characterId"
    }

    private val _state = MutableStateFlow<CharacterDetailState>(CharacterDetailState.Loading)
    val state: StateFlow<CharacterDetailState> = _state
    private val characterId: String = checkNotNull(savedStateHandle[CHARACTER_ID])

    init {
        loadCharacterInfo()
    }

    private fun loadCharacterInfo() {
        viewModelScope.launch {
            _state.value = CharacterDetailState.Loading
            getCharacterDetailUseCase(characterId).onSuccess { detail ->
                _state.value = CharacterDetailState.Success(detail.toDetailUi())
            }.onFailure {
                _state.value = CharacterDetailState.Error(it.toUserMessage())
            }
        }
    }

    fun onRetry() {
        loadCharacterInfo()
    }
}
