package me.dio.android.copa2022app.notification_scheduler

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import me.dio.android.copa2022app.domain.models.MatchDomain
import java.time.Duration
import java.time.LocalDateTime

const val NOTIFICATION_TITLE_KEY = "NOTIFICATION_TITLE_KEY"
const val NOTIFICATION_CONTENT_KEY = "NOTIFICATION_CONTENT_KEY"

class NotificationMatchesWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(
    context,
    workerParams
) {

    override fun doWork(): Result {
        val title = inputData.getString(NOTIFICATION_TITLE_KEY)
            ?: throw IllegalArgumentException("Notification title is required")
        val content = inputData.getString(NOTIFICATION_CONTENT_KEY)
            ?: throw IllegalArgumentException("Notification content is required")
        context.showNotification(title, content)

        return Result.success()
    }

    companion object {
        fun start(context: Context, match: MatchDomain) {
            val (id, _, _, team1, team2, matchDate) = match
            val initialDelay = Duration.between(LocalDateTime.now(), matchDate).minusMinutes(5)
            val inputData = workDataOf(
                NOTIFICATION_TITLE_KEY to "Se prepare que o jogo vai começar",
                NOTIFICATION_CONTENT_KEY to "Hoje tem ${team1.flag} x ${team2.flag}"
            )

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    id,
//                    ExistingWorkPolicy.KEEP,
                    ExistingWorkPolicy.REPLACE, // for test
                    createRequest(initialDelay, inputData)
                )
        }

        fun cancel(context: Context, match: MatchDomain) {
            WorkManager.getInstance(context)
                .cancelUniqueWork(match.id)
        }

        private fun createRequest(initialDelay: Duration, inputData: Data): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<NotificationMatchesWorker>()
                .setInitialDelay(initialDelay)
                .setInputData(inputData)
                .build()
    }
}
