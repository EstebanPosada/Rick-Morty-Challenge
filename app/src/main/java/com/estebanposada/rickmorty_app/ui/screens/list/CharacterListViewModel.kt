package com.estebanposada.rickmorty_app.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.domain.usecase.GetCharactersUseCase
import com.estebanposada.rickmorty_app.ui.screens.common.onFailure
import com.estebanposada.rickmorty_app.ui.screens.common.onSuccess
import com.estebanposada.rickmorty_app.ui.screens.common.toUi
import com.estebanposada.rickmorty_app.ui.screens.common.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> = _state

    private var fetchJob: Job? = null
    private var currentPage = 1
    private var hasMore = true

    init {
        loadInitial()
    }

    private fun loadInitial() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getCharactersUseCase(page = 1).onSuccess { characters ->
                val items = characters.map { it.toUi() }
                currentPage = 1
                hasMore = items.isNotEmpty()
                _state.update { it.copy(data = items, isLoading = false) }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.toUserMessage()
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        val current = _state.value

        if (current.isLoading || current.isLoadingMore || !hasMore) return
        if (fetchJob?.isActive == true) return
        fetchJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            getCharactersUseCase(currentPage++).onSuccess { characters ->
                val newItems = characters.map { it.toUi() }
                hasMore = newItems.isNotEmpty()
                currentPage++
                _state.update {
                    it.copy(
                        data = (it.data + newItems).distinctBy { item -> item.id },
                        isLoadingMore = false
                    )
                }
            }.onFailure { _state.update { it.copy(isLoadingMore = false) } }
        }
    }

    fun onRetry() {
        loadInitial()
    }
}
