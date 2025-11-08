package ca.tetervak.mathtrainer.ui.home

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
class HomeViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        repository.getQuizCountFlow()
            .map { count -> HomeUiState(quizCount = count) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeUiState(quizCount = 0)
            )

//    init {
//        viewModelScope.launch {
//            if (repository.isEmpty()) {
//                repository.insertGeneratedUserProblems()
//            }
//        }
//    }
//
//    fun makeNewProblems() {
//        viewModelScope.launch {
//            repository.emptyAndInsertGeneratedUserProblems()
//        }
//    }
}