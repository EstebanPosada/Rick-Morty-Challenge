package com.estebanposada.rickmorty_app.ui.screens.list

import com.estebanposada.rickmorty_app.ui.screens.list.components.CharacterUi

sealed class CharacterListState {
    object Loading : CharacterListState()
    data class Success(val data: List<CharacterUi>) : CharacterListState()
    data class Error(val message: String) : CharacterListState()
}
