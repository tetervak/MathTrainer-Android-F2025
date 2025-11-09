package ca.tetervak.mathtrainer.ui.quiz.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    val uiState: StateFlow<QuizListUiState> =
        quizRepository.getUserQuizzesFlow()
            .map { quizzes ->
                val stateList = quizzes.map { quiz ->
                    val score: QuizScore = quizRepository.getQuizScoreFlow(quizId = quiz.id).first()
                    QuizListItemUiState(
                        quiz = quiz,
                        quizScore = score
                    )
                }
                QuizListUiState(stateList = stateList)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = QuizListUiState(stateList = emptyList())
            )

    fun addNewQuiz() {
        quizRepository.addNewGeneratedQuiz()
    }

}
