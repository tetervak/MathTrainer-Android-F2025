package ca.tetervak.mathtrainer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.tetervak.mathtrainer.R

@Composable
fun ScoreCard(
    rightAnswers: Int,
    numberOfProblems: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, rightAnswers, numberOfProblems),
            modifier = Modifier.padding(8.dp)
        )
    }
}