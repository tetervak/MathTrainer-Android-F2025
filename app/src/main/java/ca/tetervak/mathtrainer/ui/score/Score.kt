package ca.tetervak.mathtrainer.ui.score

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.tetervak.mathtrainer.R

@Composable
fun Score(score: Int, numberOfProblems: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score, numberOfProblems),
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}