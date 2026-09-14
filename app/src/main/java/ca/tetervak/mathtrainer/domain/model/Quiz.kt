package ca.tetervak.mathtrainer.domain.model

data class Quiz(
    val id: String,
    val quizNumber: Int
){
    companion object {
        val Preview = Quiz(id = "", quizNumber = 1)
    }
}
