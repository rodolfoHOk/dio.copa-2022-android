package me.dio.android.copa2022app.data.remote.source

import me.dio.android.copa2022app.data.data.source.MatchesDataSource
import me.dio.android.copa2022app.data.remote.extensions.getOrThrowDomainError
import me.dio.android.copa2022app.data.remote.mappers.toDomain
import me.dio.android.copa2022app.data.remote.services.MatchesServices
import me.dio.android.copa2022app.domain.models.MatchDomain
import javax.inject.Inject

class MatchDataSourceRemote @Inject constructor(
    private val service: MatchesServices
): MatchesDataSource.Remote {

    override suspend fun getMatches(): List<MatchDomain>  = runCatching {
        service.getMatches()
    }.getOrThrowDomainError().toDomain()
}
