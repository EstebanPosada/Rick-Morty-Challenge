package com.estebanposada.data.models.repository

import com.estebanposada.data.characterDto
import com.estebanposada.data.characterEntity
import com.estebanposada.data.characterResponse
import com.estebanposada.data.local.CharacterDao
import com.estebanposada.data.remote.ApiService
import com.estebanposada.data.repository.CharacterRepositoryImpl
import com.estebanposada.data.toDomain
import com.estebanposada.data.toEntity
import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class CharacterRepositoryImplTest {
    private lateinit var repository: CharacterRepositoryImpl
    private lateinit var api: ApiService
    private lateinit var dao: CharacterDao

    @Before
    fun setUp() {
        api = mockk<ApiService>()
        dao = mockk<CharacterDao>()
        repository = CharacterRepositoryImpl(api, dao)
    }

    @Test
    fun `when getCharacters is called, then api throws unknown error & dao has no data`() =
        runTest {
            val page = 1

            coEvery { api.getCharacters(page) } throws RuntimeException()
            coEvery { dao.getAllCharacters() } returns emptyList()

            val result = repository.getCharacters(page)

            assert(result is Resource.Error)
            assertEquals(AppError.Unknown, (result as Resource.Error).error)
        }

    @Test
    fun `when getCharacters is called, then api throws 401 code error & dao has no data`() =
        runTest {
            val page = 1

            coEvery { api.getCharacters(page) } throws httpException(401)
            coEvery { dao.getAllCharacters() } returns emptyList()

            val result = repository.getCharacters(page)

            assert(result is Resource.Error)
            assertEquals(AppError.Unauthorized, (result as Resource.Error).error)
        }

    @Test
    fun `when getCharacters is called, then api throws unknown error & dao has data then return success`() =
        runTest {
            val page = 1
            val data = listOf(characterEntity)
            val expected = data.map { it.toDomain() }

            coEvery { api.getCharacters(page) } throws IOException()
            coEvery { dao.getAllCharacters() } returns data

            val result = repository.getCharacters(page)

            assert(result is Resource.Success)
            assertEquals(expected, (result as Resource.Success).data)
            coVerify { dao.getAllCharacters() }
        }

    @Test
    fun `when getCharacters is called, then api returns data, dao has return success`() =
        runTest {
            val page = 1
            val response = characterResponse

            coEvery { api.getCharacters(page) } returns response
            coEvery { dao.insertAll(any()) } just Runs
            coEvery { dao.getAllCharacters() } returns emptyList()

            val result = repository.getCharacters(page)

            assert(result is Resource.Success)
            coVerify { dao.insertAll(any()) }
        }

    @Test
    fun `when getCharacterById is called, dao has data, then return value`() =
        runTest {
            val id = "1"
            val character = characterEntity
            val expected = character.toDomain()

            coEvery { dao.getCharacterById(id) } returns character

            val result = repository.getCharacterById(id)

            assertEquals(expected, (result as Resource.Success).data)
            coVerify { dao.getCharacterById(id) }
        }

    @Test
    fun `when getCharacterById is called, dao has no data, then return error`() =
        runTest {
            val id = "1"

            coEvery { dao.getCharacterById(id) } returns null
            coEvery { api.getCharacterById(id) } throws RuntimeException()

            val result = repository.getCharacterById(id)

            assert(result is Resource.Error)
            assertEquals(AppError.Unknown, (result as Resource.Error).error)
            coVerify { dao.getCharacterById(id) }
        }

    @Test
    fun `when getCharacterById is called, dao has no data, then return success`() =
        runTest {
            val id = "1"
            val character = characterDto
            val characterDao = character.toEntity()

            coEvery { dao.getCharacterById(id) } returns null
            coEvery { api.getCharacterById(id) } returns character
            coEvery { dao.insert(characterDao) } just Runs

            val result = repository.getCharacterById(id)

            assertEquals(characterDao.toDomain(), (result as Resource.Success).data)
            coVerify { dao.getCharacterById(id) }
        }

    fun httpException(code: Int): HttpException {
        val response = retrofit2.Response.error<Any>(code, "".toResponseBody())
        return HttpException(response)
    }
}
