package ca.tetervak.mathtrainer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.tetervak.mathtrainer.R

@Composable
fun HomeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = stringResource(R.string.home)
        )
        Text(
            modifier = Modifier.Companion.padding(start = 8.dp),
            text = stringResource(R.string.home)
        )
    }
}