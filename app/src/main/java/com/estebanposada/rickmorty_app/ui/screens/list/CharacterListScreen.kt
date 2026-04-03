package com.estebanposada.rickmorty_app.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.estebanposada.rickmorty_app.R
import com.estebanposada.rickmorty_app.ui.screens.common.ErrorComponent
import com.estebanposada.rickmorty_app.ui.screens.list.components.CharacterListContent
import com.estebanposada.rickmorty_app.ui.screens.list.components.CharacterUi
import com.estebanposada.rickmorty_app.ui.theme.RickMortyTheme

@Composable
fun CharacterListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CharacterListScreen(
        state.value,
        modifier = modifier,
        onRetry = viewModel::onRetry,
        onLoadMore = viewModel::loadNextPage,
        onItemClick = onItemClick
    )
}

@Composable
fun CharacterListScreen(
    state: CharacterListState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onLoadMore: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading && state.data.isEmpty() -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            state.error != null && state.data.isEmpty() -> ErrorComponent(
                modifier = Modifier.align(
                    Alignment.Center
                ), message = state.error, onRetry = onRetry
            )

            else -> CharacterListContent(
                state = state,
                onLoadMore = onLoadMore,
                onItemClick = onItemClick
            )
        }
    }
}

@Preview
@Composable
private fun CharacterListLoadingPreview() {
    val state = CharacterListState(isLoading = true)
    RickMortyTheme {
        CharacterListScreen(state, onRetry = {}, onItemClick = {}, onLoadMore = {})
    }
}

@Preview
@Composable
private fun CharacterListDataPreview() {
    val data = List(3) {
        CharacterUi(
            name = "Name $it",
            imageUrl = "http://",
            id = "id$it",
            statusText = "status",
            statusColor = Color.Gray,
            genderIcon = R.drawable.ic_unknown
        )
    }
    val state = CharacterListState(data)
    RickMortyTheme {
        CharacterListScreen(state = state, onRetry = {}, onItemClick = {}, onLoadMore = {})
    }
}

@Preview
@Composable
private fun CharacterErrorDataPreview() {
    val state = CharacterListState(error = "Error")
    RickMortyTheme {
        CharacterListScreen(state = state, onRetry = {}, onItemClick = {}, onLoadMore = {})
    }
}
