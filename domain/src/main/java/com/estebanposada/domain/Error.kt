package com.estebanposada.domain

sealed class Error {
    object NetworkError : Error()
    object UnknownError : Error()
}
