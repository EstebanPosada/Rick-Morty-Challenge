package com.estebanposada.rickmorty_app.ui.screens.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estebanposada.rickmorty_app.R
import com.estebanposada.rickmorty_app.ui.screens.list.CharacterListState

@Composable
fun CharacterListContent(
    modifier: Modifier = Modifier,
    state: CharacterListState,
    onLoadMore: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = listState.layoutInfo.totalItemsCount

            lastVisibleItem != null && lastVisibleItem >= totalItems - 3
        }
    }
    LaunchedEffect(shouldLoadMore.value) { if (shouldLoadMore.value) onLoadMore() }
    LazyColumn(
        modifier = modifier,
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
            CharacterItem(item = it, onClick = onItemClick)
        }
        if (state.isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterListContentPreview() {
    CharacterListContent(
        state = CharacterListState(
            data = List(3) {
                CharacterUi(
                    id = "id$it",
                    name = "name $it",
                    statusText = "status",
                    statusColor = Color.Gray,
                    genderIcon = R.drawable.ic_gender,
                    imageUrl = "url"
                )
            }
        ),
        onLoadMore = { },
        onItemClick = { }
    )
}
