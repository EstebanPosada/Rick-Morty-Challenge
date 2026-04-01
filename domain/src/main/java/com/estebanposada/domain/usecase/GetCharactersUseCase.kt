package com.estebanposada.domain.usecase

import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.repository.CharacterRepository

class GetCharactersUseCase(val repository: CharacterRepository) {
    suspend operator fun invoke(): Resource<List<Character>> = repository.getCharacters()
}
