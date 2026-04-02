package com.estebanposada.rickmorty_app.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.estebanposada.rickmorty_app.R
import com.estebanposada.rickmorty_app.ui.screens.list.components.CharacterItem
import com.estebanposada.rickmorty_app.ui.screens.list.components.CharacterUi
import com.estebanposada.rickmorty_app.ui.theme.RickMortyTheme

@Composable
fun CharacterListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CharacterListScreen(state.value, modifier = modifier, onRetry = { viewModel.retry() })
}

@Composable
fun CharacterListScreen(
    state: CharacterListState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is CharacterListState.Success -> {
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(12.dp),
                            text = stringResource(R.string.characters),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(
                        items = state.data,
                        key = { it.id },
                        contentType = { "character" }
                    ) {
                        CharacterItem(item = it)
                    }
                }
            }

            CharacterListState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            is CharacterListState.Error ->
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text(text = "Retry", style = MaterialTheme.typography.labelMedium)
                    }
                }
        }
    }
}

@Preview
@Composable
private fun CharacterListLoadingPreview() {
    val state = CharacterListState.Loading
    RickMortyTheme {
        CharacterListScreen(state, onRetry = {})
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
            genderIcon = 0
        )
    }
    val state = CharacterListState.Success(data)
    RickMortyTheme {
        CharacterListScreen(state = state, onRetry = {})
    }
}

@Preview
@Composable
private fun CharacterErrorDataPreview() {
    val state = CharacterListState.Error("Error")
    RickMortyTheme {
        CharacterListScreen(state = state, onRetry = {})
    }
}
