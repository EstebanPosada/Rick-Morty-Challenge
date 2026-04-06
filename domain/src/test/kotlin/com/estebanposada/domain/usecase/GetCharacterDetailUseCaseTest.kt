package com.estebanposada.domain.usecase

import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import com.estebanposada.domain.character
import com.estebanposada.domain.repository.FakeCharacterRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetCharacterDetailUseCaseTest {
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private lateinit var repository: FakeCharacterRepository

    @Before
    fun setUp() {
        repository = FakeCharacterRepository()
        getCharacterDetailUseCase = GetCharacterDetailUseCase(repository)
    }

    @Test
    fun `when getCharacterDetailUseCase is called and repo returns error, then return error`() =
        runTest {
            val id = "id"

            val result = getCharacterDetailUseCase(id)

            assert(result is Resource.Error)
            assertEquals(AppError.Unknown, (result as Resource.Error).error)
        }

    @Test
    fun `when getCharacterDetailUseCase is called and repo returns success, then return data`() =
        runTest {
            val id = "id"
            val data = character

            repository.character = Resource.Success(data)
            val result = getCharacterDetailUseCase(id)

            assert(result is Resource.Success)
            assertEquals(data, (result as Resource.Success).data)
        }

}
