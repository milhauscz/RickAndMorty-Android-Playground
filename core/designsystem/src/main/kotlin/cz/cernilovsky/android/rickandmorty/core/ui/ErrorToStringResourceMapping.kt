package cz.cernilovsky.android.rickandmorty.core.ui

import androidx.annotation.StringRes
import cz.cernilovsky.android.rickandmorty.core.designsystem.R
import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.network.HttpClientException

@StringRes
fun Throwable.toMessageRes(): Int =
    when (this) {
        is HttpClientException -> error.toMessageRes()
        else -> R.string.error_unknown
    }

@StringRes
fun DataError.Remote.toMessageRes(): Int =
    when (this) {
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Remote.SERVER -> R.string.error_server
        DataError.Remote.SERIALIZATION -> R.string.error_serialization
        DataError.Remote.NOT_FOUND -> R.string.error_not_found
        DataError.Remote.UNKNOWN -> R.string.error_unknown
    }
