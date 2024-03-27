package me.dio.android.copa2022app.domain.repositories

import kotlinx.coroutines.flow.Flow
import me.dio.android.copa2022app.domain.models.Match

interface MatchesRepository {
    suspend fun getMatches(): Flow<List<Match>>
    suspend fun enableNotificationFor(id: String)
    suspend fun disableNotificationFor(id: String)
}
