package com.estebanposada.rickmorty_app.ui.screens.list

import com.estebanposada.rickmorty_app.ui.screens.list.components.CharacterUi

data class CharacterListState(
    val data: List<CharacterUi> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)
