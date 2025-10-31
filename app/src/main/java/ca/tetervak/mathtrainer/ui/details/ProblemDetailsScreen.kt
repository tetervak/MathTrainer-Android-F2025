package ca.tetervak.mathtrainer.ui.details

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ca.tetervak.mathtrainer.R
import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.UserProblem
import ca.tetervak.mathtrainer.ui.QuizTopBar
import ca.tetervak.mathtrainer.ui.score.Score
import ca.tetervak.mathtrainer.ui.score.ScoreViewModel
import ca.tetervak.mathtrainer.ui.theme.MathTrainerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemDetailsScreen(
    onHelpClick: () -> Unit,
    onHomeClick: () -> Unit,
    onListClick: () -> Unit,
    onProblemNavClick: (Int) -> Unit,
) {

    val detailsViewModel: ProblemDetailsViewModel = hiltViewModel()
    val detailsUiState: ProblemDetailsUiState by detailsViewModel.uiState.collectAsState()
    val userProblem = detailsUiState.userProblem

    val scoreViewModel: ScoreViewModel = hiltViewModel()
    val scoreData by scoreViewModel.uiState.collectAsState()

    ProblemDetailsScreenBody(
        userProblem = userProblem,
        score = scoreData.rightAnswers,
        numberOfProblems = scoreData.numberOfProblems,
        userAnswerInput = detailsViewModel.answerInput,
        onChangeUserAnswerInput = detailsViewModel::updateAnswerInput,
        onSubmit = detailsViewModel::onSubmit,
        onHelpClick = onHelpClick,
        onHomeClick = onHomeClick,
        onListClick = onListClick,
        onProblemNavClick = onProblemNavClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemDetailsScreenBody(
    userProblem: UserProblem,
    score: Int,
    numberOfProblems: Int,
    userAnswerInput: String,
    onChangeUserAnswerInput: (String) -> Unit,
    onSubmit: () -> Unit,
    onHelpClick: () -> Unit,
    onHomeClick: () -> Unit,
    onListClick: () -> Unit,
    onProblemNavClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            QuizTopBar(
                title = stringResource(R.string.problem_number, userProblem.id),
                onHelpButtonClick = onHelpClick,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            DetailsBottomBar(
                onHomeClick = onHomeClick,
                onListClick = onListClick,
                onFirstClick = { onProblemNavClick(1) }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProblemLayout(
                onUserAnswerChanged = onChangeUserAnswerInput,
                problemCount = userProblem.id,
                numberOfProblems = numberOfProblems,
                userAnswer = userAnswerInput,
                onKeyboardDone = onSubmit,
                currentProblemText = userProblem.problem.text,
                currentProblemStatus = userProblem.status,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSubmit
                ) {
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = stringResource(R.string.submit),
                        fontSize = 16.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    OutlinedButton(
                        onClick = { onProblemNavClick(userProblem.id - 1) },
                        modifier = Modifier.weight(1f),
                        enabled = userProblem.id > 1
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(R.string.previous),
                            fontSize = 16.sp
                        )
                    }
                    OutlinedButton(
                        onClick = { onProblemNavClick(userProblem.id + 1) },
                        modifier = Modifier.weight(1f),
                        enabled = userProblem.id < numberOfProblems
                    ) {
                        Text(
                            text = stringResource(R.string.next),
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = null
                        )
                    }
                }

            }
            Score(
                rightAnswers = score,
                numberOfProblems = numberOfProblems,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Composable
fun DetailsBottomBar(
    onHomeClick: () -> Unit,
    onListClick: () -> Unit,
    onFirstClick: () -> Unit
) {
    NavigationBar(
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.home)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onListClick,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(R.string.list)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onFirstClick,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Start,
                    contentDescription = stringResource(R.string.first)
                )
            }
        )
    }
}

@Composable
fun ProblemLayout(
    currentProblemText: String,
    problemCount: Int,
    numberOfProblems: Int,
    currentProblemStatus: UserAnswerStatus,
    userAnswer: String,
    onUserAnswerChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.problem_count, problemCount, numberOfProblems),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentProblemText,
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            OutlinedTextField(
                value = userAnswer,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                    errorContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserAnswerChanged,
                label = {
                    Text(stringResource(R.string.enter_your_answer))
                },
                placeholder = {
                    when (currentProblemStatus) {
                        UserAnswerStatus.WRONG_ANSWER -> {
                            Text(
                                text = stringResource(R.string.wrong_answer_try_again),
                                color = colorScheme.error
                            )
                        }

                        UserAnswerStatus.INVALID_INPUT -> {
                            Text(
                                text = stringResource(R.string.invalid_input_try_again),
                                color = colorScheme.error
                            )
                        }

                        UserAnswerStatus.NOT_ANSWERED -> {
                            Text(stringResource(R.string.enter_your_answer))
                        }

                        else -> {}
                    }
                },
                isError = currentProblemStatus == UserAnswerStatus.WRONG_ANSWER ||
                        currentProblemStatus == UserAnswerStatus.INVALID_INPUT,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            when (currentProblemStatus) {
                UserAnswerStatus.NOT_ANSWERED -> Text(
                    text = stringResource(R.string.not_answered),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Blue
                )

                UserAnswerStatus.RIGHT_ANSWER -> Text(
                    text = stringResource(R.string.right_answer),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Green
                )

                UserAnswerStatus.WRONG_ANSWER -> Text(
                    text = stringResource(R.string.wrong_answer),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Red
                )

                UserAnswerStatus.INVALID_INPUT -> Text(
                    text = stringResource(R.string.invalid_input),
                    fontStyle = FontStyle.Italic,
                    fontSize = 24.sp,
                    color = Color.Red
                )

            }
        }
    }
}

/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalActivity.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    MathTrainerTheme {
        ProblemDetailsScreenBody(
            userProblem = UserProblem(AdditionProblem(1, 2), id = 3),
            score = 2,
            numberOfProblems = 5,
            userAnswerInput = "",
            onChangeUserAnswerInput = {},
            onSubmit = {},
            onHelpClick = {},
            onHomeClick = {},
            onListClick = {},
            onProblemNavClick = {}
        )
    }
}
