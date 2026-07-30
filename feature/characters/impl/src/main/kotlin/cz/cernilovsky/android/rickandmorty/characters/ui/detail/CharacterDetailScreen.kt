package cz.cernilovsky.android.rickandmorty.characters.ui.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterGender
import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterStatus
import cz.cernilovsky.android.rickandmorty.characters.ui.ErrorMessage
import cz.cernilovsky.android.rickandmorty.characters.ui.MaxSizeLoadingIndicator
import cz.cernilovsky.android.rickandmorty.characters.ui.createKeyForSharedTransitionAvatarUrl
import cz.cernilovsky.android.rickandmorty.characters.ui.dotColor
import cz.cernilovsky.android.rickandmorty.characters.ui.toStringResource
import cz.cernilovsky.android.rickandmorty.core.ui.icon.AppIcons
import cz.cernilovsky.android.rickandmorty.core.ui.registerSharedElement
import cz.cernilovsky.android.rickandmorty.feature.characters.R
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import cz.cernilovsky.android.rickandmorty.core.designsystem.R as DesignSystemR

// Default hero image/app-bar height. Callers on large-height windows (e.g. the two-pane tablet
// layout) may pass a taller value so the hero image isn't cropped down to a sliver.
internal val IMAGE_HEIGHT = 280.dp

internal const val CHARACTER_DETAIL_CONTENT_TEST_TAG = "characterDetailContent"

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    modifier: Modifier = Modifier,
    imageHeight: Dp = IMAGE_HEIGHT,
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
) {
    // Key by id so that swapping the selected character in two-pane mode creates a fresh
    // ViewModel for the new id instead of reusing the previous character's state.
    val viewModel =
        koinViewModel<CharacterDetailViewModel>(key = characterId.toString()) {
            parametersOf(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CharacterDetailScreen(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::refresh,
        showBackButton = showBackButton,
        modifier = modifier,
        imageHeight = imageHeight,
        contentWindowInsets = contentWindowInsets,
    )
}

/**
 * [contentWindowInsets] is threaded through explicitly - a plain composable parameter, re-read
 * on every recomposition - rather than left to Scaffold's default (which resolves the ancestor
 * [androidx.compose.foundation.layout.consumeWindowInsets] chain via an attach-driven modifier
 * callback). This screen is hosted inside a HorizontalPager in the two-pane detail view, and that
 * callback isn't guaranteed to re-fire when the pager reuses a composed page for a different
 * character, which let a previous character's resolved insets leak into the next one.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    uiState: CharacterDetailUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    showBackButton: Boolean = true,
    modifier: Modifier = Modifier,
    imageHeight: Dp = IMAGE_HEIGHT,
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = contentWindowInsets,
        topBar = {
            CollapsingImageTopBar(
                name = uiState.detail?.name.orEmpty(),
                imageUrl = uiState.detail?.image,
                scrollBehavior = scrollBehavior,
                onBack = onBack,
                showBackButton = showBackButton,
                imageHeight = imageHeight,
            )
        },
    ) { innerPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
        ) {
            val detail = uiState.detail
            when {
                detail != null -> {
                    CharacterDetailContent(
                        detail = detail,
                        isLoading = uiState.isLoading,
                        errorMessage = uiState.errorMessage,
                        onRetry = onRetry,
                    )
                }

                uiState.isLoading -> {
                    MaxSizeLoadingIndicator()
                }

                uiState.errorMessage != null -> {
                    ErrorMessage(
                        error = uiState.errorMessage,
                        onRetryClicked = onRetry,
                    )
                }

                else -> {
                    MaxSizeLoadingIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollapsingImageTopBar(
    name: String,
    imageUrl: String?,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    imageHeight: Dp = IMAGE_HEIGHT,
) {
    Box {
        if (imageUrl != null) {
            val fadeBrush =
                Brush.verticalGradient(
                    0f to Color.Black,
                    0.5f to Color.Black,
                    1f to Color.Transparent,
                )
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter,
                modifier =
                    Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            alpha = 1f - scrollBehavior.state.collapsedFraction
                            compositingStrategy = CompositingStrategy.Offscreen
                        }.drawWithContent {
                            drawContent()
                            drawRect(brush = fadeBrush, blendMode = BlendMode.DstIn)
                        }.registerSharedElement(createKeyForSharedTransitionAvatarUrl(imageUrl)),
            )
        }
        LargeTopAppBar(
            title = {
                Text(
                    text = name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            navigationIcon = {
                if (showBackButton) {
                    FilledTonalIconButton(onClick = onBack) {
                        Icon(
                            imageVector = AppIcons.ArrowBack,
                            contentDescription = stringResource(R.string.button_back),
                        )
                    }
                }
            },
            expandedHeight = imageHeight,
            scrollBehavior = scrollBehavior,
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
        )
    }
}

@Composable
private fun CharacterDetailContent(
    detail: UiCharacterDetail,
    isLoading: Boolean,
    @StringRes errorMessage: Int?,
    onRetry: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag(CHARACTER_DETAIL_CONTENT_TEST_TAG),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (isLoading) {
            item {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
        item {
            CharacterSummary(detail)
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                LocationCard(
                    title = stringResource(R.string.detail_origin),
                    name = detail.originName,
                    location = detail.origin,
                    isLoading = isLoading,
                    modifier = Modifier.weight(1f),
                )
                LocationCard(
                    title = stringResource(R.string.detail_current_location),
                    name = detail.locationName,
                    location = detail.location,
                    isLoading = isLoading,
                    modifier = Modifier.weight(1f),
                )
                if (detail.type.isNotBlank()) {
                    TypeCard(
                        type = detail.type,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
        item {
            SectionTitle(stringResource(R.string.detail_episodes))
        }
        if (detail.episodes.isEmpty() && isLoading) {
            item {
                MaxWidthCircularProgressIndicator()
            }
        } else if (detail.episodes.isNotEmpty()) {
            item {
                EpisodeCarousel(detail.episodes)
            }
        }
        if (errorMessage != null) {
            item {
                DetailError(
                    errorMessage = errorMessage,
                    onRetry = onRetry,
                )
            }
        }
    }
}

@Composable
private fun DetailError(
    @StringRes errorMessage: Int,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(errorMessage),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
            )
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.button_retry))
            }
        }
    }
}

@Composable
private fun MaxWidthCircularProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CharacterSummary(detail: UiCharacterDetail) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        StatusCard(detail.status, modifier = Modifier.weight(1f))
        SpeciesCard(detail.species, modifier = Modifier.weight(1f))
        GenderCard(detail.gender, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatusCard(
    status: CharacterStatus,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier = modifier) {
        CardTitle(
            title = stringResource(R.string.character_status),
            icon = AppIcons.MonitorHeart,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier
                        .size(8.dp)
                        .background(color = status.dotColor(), shape = CircleShape),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(status.toStringResource()),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun SpeciesCard(
    species: String,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier = modifier) {
        CardTitle(
            title = stringResource(R.string.detail_species),
            icon = AppIcons.Pets,
        )
        Text(
            text = species,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun GenderCard(
    gender: CharacterGender,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier = modifier) {
        CardTitle(
            title = stringResource(R.string.detail_gender),
            icon = AppIcons.Wc,
        )
        Text(
            text = stringResource(gender.toStringResource()),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun TypeCard(
    type: String,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier = modifier) {
        CardTitle(
            title = stringResource(R.string.detail_type),
            icon = AppIcons.Category,
        )
        Text(
            text = type,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun LocationCard(
    title: String,
    name: String,
    location: UiLocation?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier = modifier) {
        CardTitle(title = title, icon = AppIcons.LocationOn)
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
        )
        if (location != null) {
            CardLabeledValue(
                label = stringResource(R.string.detail_type),
                value = location.type,
            )
            CardLabeledValue(
                label = stringResource(R.string.detail_dimension),
                value = location.dimension,
            )
        } else if (isLoading) {
            MaxWidthCircularProgressIndicator()
        }
    }
}

// A plain LazyRow rather than material3's Carousel: that experimental API's opt-in marker
// (ExperimentalMaterial3ExpressiveApi) is compiled as Kotlin-internal in the resolved material3
// artifact, making it inaccessible from here regardless of version. A horizontally scrolling row
// of fixed-width cards is a stable equivalent for this simple "browse episodes" carousel.
@Composable
private fun EpisodeCarousel(
    episodes: List<UiEpisode>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth().height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(episodes) { episode ->
            EpisodeCard(
                episode = episode,
                modifier =
                    Modifier
                        .width(220.dp)
                        .fillMaxHeight()
                        .clip(MaterialTheme.shapes.large),
            )
        }
    }
}

@Composable
private fun EpisodeCard(
    episode: UiEpisode,
    modifier: Modifier = Modifier,
) {
    DetailCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = AppIcons.LocalMovies,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${episode.episode} - ${episode.name}",
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        CardLabeledValue(
            label = stringResource(R.string.detail_air_date),
            value = episode.airDate,
        )
    }
}

@Composable
private fun DetailCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = content,
        )
    }
}

@Composable
private fun CardTitle(
    title: String,
    icon: ImageVector? = null,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun CardLabeledValue(
    label: String,
    value: String,
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Preview
@Composable
fun CharacterDetailScreenPreview() {
    MaterialTheme {
        CharacterDetailScreen(
            uiState =
                CharacterDetailUiState(
                    detail =
                        UiCharacterDetail(
                            id = 1,
                            name = "Rick Sanchez",
                            image = "",
                            status = CharacterStatus.Alive,
                            species = "Human",
                            type = "Genius",
                            gender = CharacterGender.Male,
                            originName = "Earth (C-137)",
                            origin =
                                UiLocation(
                                    id = 1,
                                    name = "Earth (C-137)",
                                    type = "Planet",
                                    dimension = "Dimension C-137",
                                ),
                            locationName = "Citadel of Ricks",
                            location = null,
                            episodes =
                                listOf(
                                    UiEpisode(
                                        id = 1,
                                        name = "Pilot",
                                        airDate = "December 2, 2013",
                                        episode = "S01E01",
                                    ),
                                    UiEpisode(
                                        id = 2,
                                        name = "Lawnmower Dog",
                                        airDate = "December 9, 2013",
                                        episode = "S01E02",
                                    ),
                                ),
                        ),
                    isLoading = false,
                ),
            onBack = {},
            onRetry = {},
        )
    }
}

@Preview
@Composable
fun CharacterDetailScreenErrorPreview() {
    MaterialTheme {
        CharacterDetailScreen(
            uiState =
                CharacterDetailUiState(
                    detail =
                        UiCharacterDetail(
                            id = 1,
                            name = "Rick Sanchez",
                            image = "",
                            status = CharacterStatus.Alive,
                            species = "Human",
                            type = "Genius",
                            gender = CharacterGender.Male,
                            originName = "Earth (C-137)",
                            origin = null,
                            locationName = "Citadel of Ricks",
                            location = null,
                            episodes = emptyList(),
                        ),
                    isLoading = false,
                    errorMessage = DesignSystemR.string.error_unknown,
                ),
            onBack = {},
            onRetry = {},
        )
    }
}

@Preview
@Composable
fun CharacterDetailLocationLoadingScreenPreview() {
    MaterialTheme {
        CharacterDetailScreen(
            uiState =
                CharacterDetailUiState(
                    detail =
                        UiCharacterDetail(
                            id = 1,
                            name = "Rick Sanchez",
                            image = "",
                            status = CharacterStatus.Alive,
                            species = "Human",
                            type = "Genius",
                            gender = CharacterGender.Male,
                            originName = "Earth (C-137)",
                            origin = null,
                            locationName = "Citadel of Ricks",
                            location = null,
                            episodes =
                                listOf(
                                    UiEpisode(
                                        id = 1,
                                        name = "Pilot",
                                        airDate = "December 2, 2013",
                                        episode = "S01E01",
                                    ),
                                    UiEpisode(
                                        id = 2,
                                        name = "Lawnmower Dog",
                                        airDate = "December 9, 2013",
                                        episode = "S01E02",
                                    ),
                                ),
                        ),
                    isLoading = true,
                ),
            onBack = {},
            onRetry = {},
        )
    }
}
