package ca.tetervak.mathtrainer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ca.tetervak.mathtrainer.domain.Quiz
import ca.tetervak.mathtrainer.domain.QuizProblem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel containing the app data and methods to process the data
 */
class QuizViewModel : ViewModel() {

    val quiz: Quiz = Quiz()

    // Game UI state
    private val _uiState: MutableStateFlow<QuizUiState> = MutableStateFlow(makeQuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState

    var answerInput by mutableStateOf("")
        private set

    fun updateAnswerInput(input: String){
        answerInput = input
    }

    fun resetGame() {
        quiz.reset()
        _uiState.value = makeQuizUiState()
    }

    fun onSkip() {
        updateAnswerInput("")
        quiz.skipProblem()
        _uiState.value = makeQuizUiState()
    }

    fun onSubmit() {
        quiz.submitAnswer(answerInput)
        _uiState.value = makeQuizUiState()
        updateAnswerInput("")
    }

    private fun makeQuizUiState(): QuizUiState =
        QuizUiState(
            numberOfProblems = quiz.numberOfProblems,
            problemCount = quiz.problemNumber,
            problem = quiz.quizProblem.problem,
            score = quiz.score,
            wrongAnswer = when(quiz.quizProblem.status){
                QuizProblem.Status.WRONG_ANSWER -> true
                QuizProblem.Status.INVALID_INPUT -> true
                else -> false
            },
            quizEnded = quiz.quizEnded
        )

}