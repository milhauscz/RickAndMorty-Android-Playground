package cz.cernilovsky.android.rickandmorty.characters.ui

import androidx.annotation.StringRes
import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterGender
import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterStatus
import cz.cernilovsky.android.rickandmorty.feature.characters.R

@StringRes
fun CharacterStatus.toStringResource(): Int =
    when (this) {
        CharacterStatus.Alive -> R.string.character_status_alive
        CharacterStatus.Dead -> R.string.character_status_dead
        CharacterStatus.Unknown -> R.string.character_status_unknown
    }

@StringRes
fun CharacterGender.toStringResource(): Int =
    when (this) {
        CharacterGender.Female -> R.string.character_gender_female
        CharacterGender.Male -> R.string.character_gender_male
        CharacterGender.Genderless -> R.string.character_gender_genderless
        CharacterGender.Unknown -> R.string.character_gender_unknown
    }
