package com.acet.moviescinemawithkmp

import android.icu.number.Scale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.acet.moviescinemawithkmp.extention.ColorExtension.parseColor
import com.acet.moviescinemawithkmp.ui.theme.MoviesCinemaWithKmpTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun MoviesRoot(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    uiEvent: MainUiEvent
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MoviesScreen(
        modifier = modifier,
        uiState = uiState,
        uiEvent = uiEvent.copy(
            onPullRefresh = viewModel::onPullRefresh
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    uiEvent: MainUiEvent
) {
    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            PullToRefreshBox(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
                isRefreshing = uiState.isLoading,
                onRefresh = {
                    uiEvent.onPullRefresh()
                },
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    columns = GridCells.Fixed(2),
                ) {
                    items(uiState.movies) { item ->
                        MovieItem(
                            modifier = Modifier,
                            movieImageUrl = item.poster_path.orEmpty(),
                            movieName = item.title.orEmpty(),
                            movieDescription = item.overview.orEmpty()
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movieImageUrl: String,
    movieName: String,
    movieDescription: String,
) {
    val colors = listOf("#FF5630", "#FF9D0D", "#8BC34A", "#467669", "#01B8AA", "#42A5F5", "#1A77F2", "#CA58FF")

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = colors[Random.nextInt(colors.size)].parseColor() ?: Color.Gray
        ),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                model = movieImageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Text(
                text = movieName,
                color = Color.White,
                fontWeight = FontWeight.Bold,

            )

            Text(
                text = movieDescription,
                maxLines = 5,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    MoviesCinemaWithKmpTheme {
        MoviesScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = MainUiState(),
            uiEvent = MainUiEvent()
        )
    }
}