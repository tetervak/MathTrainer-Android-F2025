package ca.tetervak.mathtrainer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ca.tetervak.mathtrainer.domain.UserQuiz
import ca.tetervak.mathtrainer.domain.UserProblem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel containing the app data and methods to process the data
 */
class QuizViewModel : ViewModel() {

    val userQuiz: UserQuiz = UserQuiz()

    // Game UI state
    private val _uiState: MutableStateFlow<QuizUiState> = MutableStateFlow(makeQuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState

    var answerInput by mutableStateOf("")
        private set

    fun updateAnswerInput(input: String){
        answerInput = input
    }

    fun resetGame() {
        userQuiz.reset()
        _uiState.value = makeQuizUiState()
    }

    fun onSkip() {
        updateAnswerInput("")
        userQuiz.skipProblem()
        _uiState.value = makeQuizUiState()
    }

    fun onSubmit() {
        userQuiz.submitAnswer(answerInput)
        _uiState.value = makeQuizUiState()
        updateAnswerInput("")
    }

    private fun makeQuizUiState(): QuizUiState =
        QuizUiState(
            numberOfProblems = userQuiz.numberOfProblems,
            problemCount = userQuiz.problemNumber,
            problem = userQuiz.userProblem.problem,
            score = userQuiz.score,
            wrongAnswer = when(userQuiz.userProblem.status){
                UserProblem.Status.WRONG_ANSWER -> true
                UserProblem.Status.INVALID_INPUT -> true
                else -> false
            },
            quizEnded = userQuiz.quizEnded
        )

}