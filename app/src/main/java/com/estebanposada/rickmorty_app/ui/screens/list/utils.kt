package com.estebanposada.rickmorty_app.ui.screens.list

import androidx.compose.ui.graphics.Color
import com.estebanposada.domain.models.Gender
import com.estebanposada.domain.models.Status
import com.estebanposada.rickmorty_app.ui.theme.AliveColor
import com.estebanposada.rickmorty_app.ui.theme.DeadColor
import com.estebanposada.rickmorty_app.ui.theme.UnknownColor

fun Gender.icon() = when (this) {
    Gender.MALE -> com.estebanposada.rickmorty_app.R.drawable.ic_male
    Gender.FEMALE -> com.estebanposada.rickmorty_app.R.drawable.ic_female
    Gender.UNKNOWN -> com.estebanposada.rickmorty_app.R.drawable.ic_unknown
    Gender.OTHER -> com.estebanposada.rickmorty_app.R.drawable.ic_alien
}

fun Status.color(): Color = when (this) {
    Status.ALIVE -> AliveColor
    Status.DEAD -> DeadColor
    Status.UNKNOWN -> UnknownColor
}
