package ca.tetervak.mathtrainer.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.UserProblemRepository
import ca.tetervak.mathtrainer.domain.model.ScoreData
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    repository: UserProblemRepository
) : ViewModel() {

    val uiState: StateFlow<ScoreData> = repository.getScoreDataFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScoreData(numberOfProblems = 0, rightAnswers = 0)
        )

}