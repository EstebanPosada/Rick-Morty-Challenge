package com.estebanposada.rickmorty_app.ui.screens.common

import androidx.compose.ui.graphics.Color
import com.estebanposada.domain.AppError
import com.estebanposada.domain.models.Gender
import com.estebanposada.domain.models.Status
import com.estebanposada.rickmorty_app.R
import com.estebanposada.rickmorty_app.ui.theme.AliveColor
import com.estebanposada.rickmorty_app.ui.theme.DeadColor
import com.estebanposada.rickmorty_app.ui.theme.UnknownColor

fun Gender.icon() = when (this) {
    Gender.MALE -> R.drawable.ic_male
    Gender.FEMALE -> R.drawable.ic_female
    Gender.UNKNOWN -> R.drawable.ic_unknown
    Gender.OTHER -> R.drawable.ic_alien
}

fun Status.color(): Color = when (this) {
    Status.ALIVE -> AliveColor
    Status.DEAD -> DeadColor
    Status.UNKNOWN -> UnknownColor
}

fun AppError.toUserMessage() = when (this) {
    AppError.Network -> "Check your internet connection."
    AppError.Unauthorized -> "You are not authorized"
    AppError.Unknown -> "Unexpected error occurred"
}
