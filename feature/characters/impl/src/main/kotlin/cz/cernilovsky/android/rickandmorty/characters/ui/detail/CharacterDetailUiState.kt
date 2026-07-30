package cz.cernilovsky.android.rickandmorty.characters.ui.detail

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class CharacterDetailUiState(
    val detail: UiCharacterDetail? = null,
    val isLoading: Boolean = true,
    @StringRes val errorMessage: Int? = null,
)
