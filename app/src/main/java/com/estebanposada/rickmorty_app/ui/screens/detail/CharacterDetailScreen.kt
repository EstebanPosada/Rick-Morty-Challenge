package com.estebanposada.rickmorty_app.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.estebanposada.rickmorty_app.R
import com.estebanposada.rickmorty_app.ui.screens.common.StatusBadge
import com.estebanposada.rickmorty_app.ui.screens.detail.components.DetailUi
import com.estebanposada.rickmorty_app.ui.screens.detail.components.InfoRow

@Composable
fun CharacterDetailScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CharacterDetailScreen(modifier, state.value, onRetry = viewModel::onRetry)
}

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    state: CharacterDetailState,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        when (state) {
            is CharacterDetailState.Error -> Column(
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

            CharacterDetailState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            is CharacterDetailState.Success -> {
                val context = LocalContext.current
                val imageRequest =
                    ImageRequest.Builder(context).data(state.data.imageUrl).crossfade(false)
                        .size(150).build()
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            AsyncImage(
                                model = imageRequest,
                                contentDescription = state.data.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.ic_unknown),
                                error = painterResource(R.drawable.ic_alien),
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = state.data.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            StatusBadge(
                                modifier = Modifier,
                                text = state.data.statusText,
                                color = state.data.statusColor
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Info",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                InfoRow(
                                    label = "Status",
                                    value = state.data.statusText,
                                    icon = R.drawable.ic_species
                                )
                                InfoRow(
                                    label = "Type",
                                    value = state.data.type.ifEmpty { "Unknown" },
                                    icon = R.drawable.ic_type
                                )
                                InfoRow(
                                    label = "Gender",
                                    value = state.data.gender,
                                    icon = state.data.genderIcon
                                )
                            }
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Location",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                InfoRow(
                                    label = "Origin",
                                    value = state.data.statusText,
                                    icon = R.drawable.ic_origin
                                )
                                InfoRow(
                                    label = "Location",
                                    value = state.data.type.ifEmpty { "Unknown" },
                                    icon = R.drawable.ic_species
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterDetailScreenPreview() {
    val detail = DetailUi(
        name = "Name",
        imageUrl = "http://",
        id = "id",
        gender = "gen",
        type = "type",
        statusText = "status",
        statusColor = Color.Gray,
        genderIcon = R.drawable.ic_gender
    )
    val state = CharacterDetailState.Success(detail)
    CharacterDetailScreen(state = state, onRetry = {})
}
