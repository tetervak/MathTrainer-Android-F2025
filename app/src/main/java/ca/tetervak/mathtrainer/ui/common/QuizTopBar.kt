package ca.tetervak.mathtrainer.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ca.tetervak.mathtrainer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onHelpClick: () -> Unit,
    onBackClick: (() -> Unit)? = null
) = CenterAlignedTopAppBar(
    title = {
        Text(
            text = title,
            fontSize = 24.sp
        )
    },
    navigationIcon = {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    },
    actions = {
        IconButton(
            onClick = onHelpClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_help_outline_24),
                contentDescription = stringResource(R.string.about)
            )
        }
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.primary,
        actionIconContentColor = MaterialTheme.colorScheme.primary
    ),
    scrollBehavior = scrollBehavior,
)