package com.estebanposada.data.models.remote

import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> = try {
    Resource.Success(apiCall())
} catch (e: IOException) {
    Resource.Error(AppError.Network)
} catch (e: HttpException) {
    when (e.code()) {
        401 -> Resource.Error(AppError.Unauthorized)
        else -> Resource.Error(AppError.Unknown)
    }
} catch (e: Exception) {
    Resource.Error(AppError.Unknown)
}
