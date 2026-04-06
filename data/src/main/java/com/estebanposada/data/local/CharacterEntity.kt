package com.estebanposada.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val status: String,
    val gender: String,
    val species: String,
    val type: String,
    val image: String
)
