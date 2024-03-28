package me.dio.android.copa2022app.data.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import me.dio.android.copa2022app.data.data.source.MatchesDataSource
import me.dio.android.copa2022app.domain.models.Match
import me.dio.android.copa2022app.domain.repositories.MatchesRepository
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val localDataSource: MatchesDataSource.Local,
    private val remoteDataSource: MatchesDataSource.Remote,
) : MatchesRepository {

    override suspend fun getMatches(): Flow<List<Match>> {
        return flowOf(remoteDataSource.getMatches())
            .combine(localDataSource.getActiveNotificationIds()) { matches, ids ->
                matches.map { match -> match.copy(notificationEnabled = ids.contains(match.id)) }
            }
    }

    override suspend fun enableNotificationFor(id: String) {
        localDataSource.enableNotificationFor(id)
    }

    override suspend fun disableNotificationFor(id: String) {
        localDataSource.disableNotificationFor(id)
    }
}
