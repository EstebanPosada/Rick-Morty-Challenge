package com.estebanposada.domain

sealed class AppError {
    object Network : AppError()
    object Unknown : AppError()
    object Unauthorized : AppError()
}
