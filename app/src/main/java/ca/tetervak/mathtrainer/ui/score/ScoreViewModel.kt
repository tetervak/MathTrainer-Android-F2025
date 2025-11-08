package ca.tetervak.mathtrainer.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import ca.tetervak.mathtrainer.domain.model.QuizScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    repository: QuizRepository
) : ViewModel() {

    val uiState: StateFlow<QuizScore> = repository.getQuizScoreFlow("")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuizScore(numberOfProblems = 0, rightAnswers = 0)
        )

}