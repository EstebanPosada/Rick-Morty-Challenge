package com.estebanposada.rickmorty_app.ui.screens.common

import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onFailure(action: (AppError) -> Unit): Resource<T> {
    if (this is Resource.Error) action(error)
    return this
}
