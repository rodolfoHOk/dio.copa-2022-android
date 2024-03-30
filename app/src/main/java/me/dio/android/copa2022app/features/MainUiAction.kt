package me.dio.android.copa2022app.features

import me.dio.android.copa2022app.domain.models.MatchDomain

sealed interface MainUiAction {

    data class MatchesNotFound(val message: String) : MainUiAction

    data class Unexpected(val message: String) : MainUiAction

    data class EnableNotification(val match: MatchDomain) : MainUiAction

    data class DisableNotification(val match: MatchDomain) : MainUiAction
}
