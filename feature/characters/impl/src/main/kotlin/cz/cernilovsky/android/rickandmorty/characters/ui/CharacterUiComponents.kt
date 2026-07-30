package cz.cernilovsky.android.rickandmorty.characters.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cernilovsky.android.rickandmorty.feature.characters.R

/**
 * Shared full-size states used by the list, detail and list-detail screens (hence in the parent
 * `ui` package rather than a screen-specific subpackage).
 */
@Composable
fun MaxSizeLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(
    @StringRes error: Int,
    onRetryClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(error),
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        Button(
            onClick = onRetryClicked,
        ) {
            Text(
                text = stringResource(R.string.button_retry),
            )
        }
    }
}
