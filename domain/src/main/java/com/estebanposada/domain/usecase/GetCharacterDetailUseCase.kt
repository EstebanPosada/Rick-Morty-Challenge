package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.repository.CharacterRepository

class GetCharacterDetailUseCase(val repository: CharacterRepository) {
    suspend operator fun invoke(id: String): Resource<Character> = repository.getCharacterById(id)
}
