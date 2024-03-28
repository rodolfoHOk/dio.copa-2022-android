package me.dio.android.copa2022app.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.dio.android.copa2022app.domain.models.Match
import me.dio.android.copa2022app.domain.repositories.MatchesRepository
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {

    suspend operator fun invoke(): Flow<List<Match>> {
        return repository.getMatches()
    }
}
