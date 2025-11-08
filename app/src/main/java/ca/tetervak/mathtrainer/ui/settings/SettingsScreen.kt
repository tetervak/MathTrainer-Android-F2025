package ca.tetervak.mathtrainer.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ca.tetervak.mathtrainer.R
import ca.tetervak.mathtrainer.domain.model.ProblemGeneration
import ca.tetervak.mathtrainer.domain.model.UserPreferences
import ca.tetervak.mathtrainer.ui.QuizTopBar
import ca.tetervak.mathtrainer.ui.theme.MathTrainerTheme

@Composable
fun SettingsScreen(
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit,
){
    val viewModel: SettingsViewModel = hiltViewModel()
    val uiState: UserPreferences by viewModel.uiState.collectAsState()

    SettingsScreenBody(
        numberOfProblems = uiState.numberOfProblems,
        problemGeneration = uiState.problemGeneration,
        onNumberOfProblemsChange = viewModel::saveNumberOfProblems,
        onProblemGenerationChange = viewModel::saveProblemGeneration,
        onHomeClick = onHomeClick,
        onHelpClick = onHelpClick
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenBody(
    numberOfProblems: Int,
    problemGeneration: ProblemGeneration,
    onNumberOfProblemsChange: (Int) -> Unit,
    onProblemGenerationChange: (ProblemGeneration) -> Unit,
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            QuizTopBar(
                title = stringResource(R.string.settings),
                onHelpClick = onHelpClick,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            NumberOfProblemsInput(
                numberOfProblems = numberOfProblems,
                onChange = onNumberOfProblemsChange
            )
            ProblemGenerationInput(
                problemGeneration = problemGeneration,
                onChange = onProblemGenerationChange
            )
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberOfProblemsInput(
    numberOfProblems: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectExpanded: Boolean by remember { mutableStateOf(false) }
    val choices = listOf(5, 10, 15, 20)
    val problems: String = stringResource(R.string.problems)
    val selectOptions: List<String> = choices.map { number -> "$number $problems" }

    val selectedText = selectOptions[choices.indexOf(numberOfProblems)]

    ExposedDropdownMenuBox(
        expanded = selectExpanded,
        onExpandedChange = { selectExpanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(R.string.number_of_problems),
                    fontSize = 14.sp
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = selectExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                color = colorResource(id = R.color.purple_500)
            ),
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(
            expanded = selectExpanded,
            onDismissRequest = {
                selectExpanded = false
            }
        ) {
            choices.forEachIndexed { index, choice ->
                DropdownMenuItem(
                    onClick = {
                        selectExpanded = false
                        onChange(choice)
                    },
                    text = {
                        Text(text = selectOptions[index], fontSize = 20.sp)
                    }
                )
            }
        }
    }
}

@Composable
fun ProblemGenerationInput(
    problemGeneration: ProblemGeneration,
    onChange: (ProblemGeneration) -> Unit
) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text(
            text = stringResource(R.string.problem_generation),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onChange(ProblemGeneration.LOCAL) }
        ) {
            RadioButton(
                selected = problemGeneration == ProblemGeneration.LOCAL,
                onClick = null,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.local),
                fontSize = 18.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onChange(ProblemGeneration.REMOTE) }
        ) {
            RadioButton(
                selected = problemGeneration == ProblemGeneration.REMOTE,
                onClick = null,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = stringResource(id = R.string.remote),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
@Preview
fun SettingsScreenBodyPreview(){
    MathTrainerTheme {
        SettingsScreenBody(
            numberOfProblems = 10,
            problemGeneration = ProblemGeneration.LOCAL,
            onNumberOfProblemsChange = {},
            onProblemGenerationChange = {},
            onHomeClick = {},
            onHelpClick = {}
        )
    }
}