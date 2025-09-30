package ca.tetervak.mathtrainer.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import ca.tetervak.mathtrainer.ui.AboutDialog
import ca.tetervak.mathtrainer.ui.QuizTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemListScreen(){
    val viewModel: ProblemListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val list: List<UserProblem> = uiState.problemList

    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            QuizTopBar(
                title = stringResource(R.string.problem_list),
                onHelpButtonClick = { showAboutDialog = true },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(list.size) { index ->
                ProblemListItem(userProblem = list[index])
            }
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismissRequest = { showAboutDialog = false })
    }
}

@Composable
fun ProblemListItem(userProblem: UserProblem, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
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
            when(userProblem.status){
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
    ProblemListItem(userProblem = UserProblem(problem = AdditionProblem(1, 2), id = 3))
}