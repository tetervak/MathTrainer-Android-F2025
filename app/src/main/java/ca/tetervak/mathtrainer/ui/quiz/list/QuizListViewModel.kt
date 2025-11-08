package ca.tetervak.mathtrainer.ui.quiz.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    val uiState: StateFlow<QuizListUiState> =
        quizRepository.getUserQuizzesFlow()
            .map { quizzes -> QuizListUiState(quizList = quizzes)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = QuizListUiState(quizList = emptyList()))

//    fun addGeneratedQuiz() {
//        viewModelScope.launch {
//            quizRepository.insertGeneratedQuiz()
//        }
//    }

}
