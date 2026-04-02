package com.estebanposada.rickmorty_app.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.usecase.GetCharactersUseCase
import com.estebanposada.rickmorty_app.ui.screens.common.onFailure
import com.estebanposada.rickmorty_app.ui.screens.common.onSuccess
import com.estebanposada.rickmorty_app.ui.screens.common.toUi
import com.estebanposada.rickmorty_app.ui.screens.common.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<CharacterListState>(CharacterListState.Loading)
    val state: StateFlow<CharacterListState> = _state

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _state.value = CharacterListState.Loading
            getCharactersUseCase().onSuccess { characters ->
                _state.value = CharacterListState.Success(characters.map { it.toUi() })
            }.onFailure { error ->
                _state.value = CharacterListState.Error(error.toUserMessage())
            }
        }
    }

    fun retry() {
        fetchCharacters()
    }
}
