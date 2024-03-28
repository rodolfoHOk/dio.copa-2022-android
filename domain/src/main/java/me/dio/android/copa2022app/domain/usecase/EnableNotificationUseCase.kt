package me.dio.android.copa2022app.domain.usecase

import me.dio.android.copa2022app.domain.repositories.MatchesRepository
import javax.inject.Inject

class EnableNotificationUseCase @Inject constructor(
    private val repository: MatchesRepository
) {

    suspend operator fun invoke(id: String): Unit {
        repository.enableNotificationFor(id)
    }
}
