package me.dio.android.copa2022app.features

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.dio.android.copa2022app.core.BaseViewModel
import me.dio.android.copa2022app.data.remote.exceptions.NotFoundException
import me.dio.android.copa2022app.data.remote.exceptions.UnexpectedException
import me.dio.android.copa2022app.domain.models.MatchDomain
import me.dio.android.copa2022app.domain.usecase.DisableNotificationUseCase
import me.dio.android.copa2022app.domain.usecase.EnableNotificationUseCase
import me.dio.android.copa2022app.domain.usecase.GetMatchesUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase
) : BaseViewModel<MainUiState, MainUiAction>(MainUiState()) {

    init {
        fetchMatches()
    }

    private fun fetchMatches() = viewModelScope.launch {
        getMatchesUseCase()
            .flowOn(Dispatchers.Main)
            .catch {
                when (it) {
                    is NotFoundException -> sendAction(
                        MainUiAction.MatchesNotFound(
                            it.message ?: "Ops! Não conseguimos encontrar as partidas :'("
                        )
                    )

                    is UnexpectedException -> sendAction(MainUiAction.Unexpected)
                }
            }.collect { matches ->
                setState {
                    copy(matches = matches)
                }
            }
    }

    fun toggleNotification(match: MatchDomain) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.Main) {
                    val action = if (match.notificationEnabled) {
                        disableNotificationUseCase(match.id)
                        MainUiAction.DisableNotification(match)
                    } else {
                        enableNotificationUseCase(match.id)
                        MainUiAction.EnableNotification(match)
                    }
                    sendAction(action)
                }
            }
        }
    }
}
