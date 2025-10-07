package ca.tetervak.mathtrainer.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ca.tetervak.mathtrainer.R
import ca.tetervak.mathtrainer.ui.QuizTopBar
import ca.tetervak.mathtrainer.ui.score.ScoreViewModel
import ca.tetervak.mathtrainer.ui.score.Score
import ca.tetervak.mathtrainer.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFirstClick: () -> Unit,
    onListClick: () -> Unit,
    onHelpClick: () -> Unit,
) {

    val homeViewModel: HomeViewModel = hiltViewModel()

    val scoreViewModel: ScoreViewModel = hiltViewModel()
    val scoreUiState by scoreViewModel.uiState.collectAsState()

    HomeScreenBody(
        score = scoreUiState.score,
        numberOfProblems = scoreUiState.numberOfProblems,
        onFirstClick = onFirstClick,
        onListClick = onListClick,
        onMakeNewProblemsClick = {
            homeViewModel.makeNewProblems()
            onListClick()
        },
        onHelpClick = onHelpClick
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeScreenBody(
    score: Int,
    numberOfProblems: Int,
    onFirstClick: () -> Unit,
    onListClick: () -> Unit,
    onMakeNewProblemsClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            QuizTopBar(
                title = stringResource(R.string.home),
                onHelpButtonClick = onHelpClick,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(48.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 32.sp,
                color = Purple40
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onFirstClick
            ) {

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.first_problem)
                )
                Icon(
                    imageVector = Icons.Filled.Start,
                    contentDescription = stringResource(R.string.first)
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onListClick
            ) {

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.list_all_problems)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(R.string.list)
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onMakeNewProblemsClick
            ) {

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.make_new_problems)
                )
                Icon(
                    imageVector = Icons.Filled.Replay,
                    contentDescription = stringResource(R.string.first)
                )
            }
            Score(
                score = score,
                numberOfProblems = numberOfProblems,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenBody(
        score = 3,
        numberOfProblems = 5,
        onFirstClick = {},
        onListClick = {},
        onMakeNewProblemsClick = {},
        onHelpClick = {}
    )
}