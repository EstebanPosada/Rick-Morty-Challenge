package com.estebanposada.rickmorty_app.ui.screens.detail

import com.estebanposada.rickmorty_app.ui.screens.detail.components.DetailUi

sealed class CharacterDetailState {
    object Loading : CharacterDetailState()
    data class Success(val data: DetailUi) : CharacterDetailState()
    data class Error(val message: String) : CharacterDetailState()
}
