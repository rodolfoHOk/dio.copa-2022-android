package me.dio.android.copa2022app.features

import me.dio.android.copa2022app.domain.models.MatchDomain

data class MainUiState(

    val matches: List<MatchDomain> = emptyList()
)
