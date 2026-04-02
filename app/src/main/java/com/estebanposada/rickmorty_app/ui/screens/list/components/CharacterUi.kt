package com.estebanposada.rickmorty_app.ui.screens.list.components

import androidx.compose.ui.graphics.Color

data class CharacterUi(
    val id: String,
    val name: String,
    val statusText: String,
    val statusColor: Color,
    val genderIcon: Int,
    val imageUrl: String
)
