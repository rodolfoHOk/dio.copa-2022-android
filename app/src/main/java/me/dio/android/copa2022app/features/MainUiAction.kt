package me.dio.android.copa2022app.features

sealed class MainUiAction {

    data class MatchesNotFound(val message: String) : MainUiAction()

    data object Unexpected : MainUiAction()
}
