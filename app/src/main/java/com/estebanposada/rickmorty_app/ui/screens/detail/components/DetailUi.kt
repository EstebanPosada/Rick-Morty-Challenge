package com.estebanposada.rickmorty_app.ui.screens.detail.components

import androidx.compose.ui.graphics.Color

data class DetailUi(
    val id: String,
    val name: String,
    val statusText: String,
    val statusColor: Color,
    val type: String,
    val gender: String,
    val genderIcon: Int,
    val imageUrl: String
)
