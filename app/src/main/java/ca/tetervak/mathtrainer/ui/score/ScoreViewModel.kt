package ca.tetervak.mathtrainer.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import ca.tetervak.mathtrainer.domain.model.ScoreData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    repository: QuizRepository
) : ViewModel() {

    val uiState: StateFlow<ScoreData> = repository.getQuizScoreDataFlow("")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScoreData(numberOfProblems = 0, rightAnswers = 0)
        )

}