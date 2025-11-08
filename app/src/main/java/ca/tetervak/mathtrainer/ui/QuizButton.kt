package ca.tetervak.mathtrainer.ui

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ca.tetervak.mathtrainer.R

@Composable
fun QuizButton(
    quizNumber: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.quiz_n, quizNumber),
            fontSize = 16.sp
        )
    }
}