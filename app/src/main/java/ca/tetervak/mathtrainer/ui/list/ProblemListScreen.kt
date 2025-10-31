package ca.tetervak.mathtrainer.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ca.tetervak.mathtrainer.R
import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.domain.UserProblem
import ca.tetervak.mathtrainer.ui.QuizTopBar
import ca.tetervak.mathtrainer.ui.score.Score
import ca.tetervak.mathtrainer.ui.score.ScoreViewModel
import ca.tetervak.mathtrainer.ui.theme.MathTrainerTheme

@Composable
fun ProblemListScreen(
    onProblemClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    val viewModel: ProblemListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val list: List<UserProblem> = uiState.problemList

    val scoreViewModel: ScoreViewModel = hiltViewModel()
    val scoreUiState by scoreViewModel.uiState.collectAsState()

    ProblemListScreenBody(
        list = list,
        numberOfProblems = scoreUiState.numberOfProblems,
        score = scoreUiState.score,
        onProblemClick = onProblemClick,
        onHomeClick = onHomeClick,
        onHelpClick = onHelpClick
    )
}

@Composable
fun ProblemListScreenPhoneHorizontal(
    onProblemClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    val viewModel: ProblemListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val list: List<UserProblem> = uiState.problemList

    val scoreViewModel: ScoreViewModel = hiltViewModel()
    val scoreUiState by scoreViewModel.uiState.collectAsState()

    ProblemListScreenBodyPhoneHorizontal(
        list = list,
        numberOfProblems = scoreUiState.numberOfProblems,
        score = scoreUiState.score,
        onProblemClick = onProblemClick,
        onHomeClick = onHomeClick,
        onHelpClick = onHelpClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemListScreenBodyPhoneHorizontal(
    list: List<UserProblem>,
    numberOfProblems: Int,
    score: Int,
    onProblemClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            QuizTopBar(
                title = stringResource(R.string.problem_list),
                onHelpButtonClick = onHelpClick,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyVerticalGrid (
                columns = GridCells.Fixed(count = 2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(items = list) { userProblem ->
                    ProblemListItem(
                        onClick = { onProblemClick(userProblem.id) },
                        userProblem = userProblem
                    )
                }
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier
                        //.fillMaxWidth()
                        .padding(16.dp),
                    onClick = onHomeClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(R.string.home)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.home)
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
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemListScreenBody(
    list: List<UserProblem>,
    numberOfProblems: Int,
    score: Int,
    onProblemClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            QuizTopBar(
                title = stringResource(R.string.problem_list),
                onHelpButtonClick = onHelpClick,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(items = list) { userProblem ->
                    ProblemListItem(
                        onClick = { onProblemClick(userProblem.id) },
                        userProblem = userProblem
                    )
                }
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(16.dp),
                    onClick = onHomeClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = stringResource(R.string.home)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.home)
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
}


@Composable
fun ProblemListItem(
    userProblem: UserProblem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${userProblem.id}.",
                fontSize = 24.sp,
                style = TextStyle(fontWeight = FontWeight.Bold),
                color = Color.DarkGray
            )
            Text(
                text = userProblem.problem.text,
                fontSize = 24.sp
            )
            when (userProblem.status) {
                UserProblem.Status.NOT_ANSWERED -> Text(
                    text = stringResource(R.string.not_answered),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Blue
                )

                UserProblem.Status.RIGHT_ANSWER -> Text(
                    text = stringResource(R.string.right_answer),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Green
                )

                UserProblem.Status.WRONG_ANSWER -> Text(
                    text = stringResource(R.string.wrong_answer),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Red
                )

                UserProblem.Status.INVALID_INPUT -> Text(
                    text = stringResource(R.string.invalid_input),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Red
                )

            }
        }

    }
}

@Preview
@Composable
fun ProblemListItemPreview() {
    MathTrainerTheme {
        ProblemListItem(
            userProblem = UserProblem(problem = AdditionProblem(1, 2), id = 3),
            onClick = {}
        )
    }
}

@Preview
@Composable
fun ProblemListScreenBodyPreview() {
    MathTrainerTheme {
        ProblemListScreenBody(
            list = List(5) {
                UserProblem(problem = AdditionProblem(1, 2), id = it + 1)
            },
            numberOfProblems = 5,
            score = 0,
            onProblemClick = {},
            onHomeClick = {},
            onHelpClick = {}
        )
    }
}