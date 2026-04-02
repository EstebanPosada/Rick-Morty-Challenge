package com.estebanposada.rickmorty_app.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.Resource
import com.estebanposada.domain.usecase.GetCharactersUseCase
import com.estebanposada.rickmorty_app.ui.screens.common.toUi
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
            when (val result = getCharactersUseCase()) {
                is Resource.Success -> _state.value =
                    CharacterListState.Success(result.data.map { it.toUi() })

                is Resource.Error -> _state.value =
                    CharacterListState.Error(result.error.toString())
            }
        }
    }

    fun retry() {
        fetchCharacters()
    }
}
