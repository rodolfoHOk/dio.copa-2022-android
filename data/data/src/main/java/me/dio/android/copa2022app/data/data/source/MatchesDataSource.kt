package me.dio.android.copa2022app.data.data.source

import kotlinx.coroutines.flow.Flow
import me.dio.android.copa2022app.domain.models.Match

sealed interface MatchesDataSource {
    interface Local : MatchesDataSource {
        fun getActiveNotificationIds(): Flow<Set<String>>
        suspend fun enableNotificationFor(id: String)
        suspend fun disableNotificationFor(id: String)
    }

    interface Remote : MatchesDataSource {
        suspend fun getMatches(): List<Match>
    }
}
