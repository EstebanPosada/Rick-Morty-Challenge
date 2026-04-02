package com.estebanposada.rickmorty_app.ui.screens.common

import com.estebanposada.domain.models.Character
import com.estebanposada.rickmorty_app.ui.screens.list.CharacterUi
import com.estebanposada.rickmorty_app.ui.screens.list.color
import com.estebanposada.rickmorty_app.ui.screens.list.icon

fun Character.toUi() = CharacterUi(
    id = id,
    name = name,
    statusText = status.name.lowercase().replaceFirstChar { it.uppercase() },
    statusColor = status.color(),
    genderIcon = gender.icon(),
    imageUrl = image
)
