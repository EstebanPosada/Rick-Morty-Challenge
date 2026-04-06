package com.estebanposada.domain.usecase

import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import com.estebanposada.domain.character
import com.estebanposada.domain.repository.FakeCharacterRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetCharactersUseCaseTest {
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var repository: FakeCharacterRepository

    @Before
    fun setUp() {
        repository = FakeCharacterRepository()
        getCharactersUseCase = GetCharactersUseCase(repository)
    }



    @Test
    fun `when getCharactersUseCase is called and repo returns error, then return error`() =
        runTest {
            val page = 1

            val result = getCharactersUseCase(page)

            assert(result is Resource.Error)
            assertEquals(AppError.Unknown, (result as Resource.Error).error)
        }

    @Test
    fun `when getCharactersUseCase is called and repo returns success, then return data`() =
        runTest {
            val page = 1
            val data = listOf(character)

            repository.characters = Resource.Success(data)
            val result = getCharactersUseCase(page)

            assert(result is Resource.Success)
            assertEquals(data, (result as Resource.Success).data)
        }
}
