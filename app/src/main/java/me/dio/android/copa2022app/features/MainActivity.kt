package me.dio.android.copa2022app.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import me.dio.android.copa2022app.extensions.observe
import me.dio.android.copa2022app.notification_scheduler.NotificationMatchesWorker
import me.dio.android.copa2022app.ui.theme.Copa2022AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeActions()

        setContent {
            Copa2022AppTheme {
                val state by viewModel.state.collectAsState()
                MainScreen(
                    matches = state.matches,
                    onNotificationClick = viewModel::toggleNotification
                )
            }
        }
    }

    private fun observeActions() {
        viewModel.action.observe(this) { action ->
            when (action) {
                is MainUiAction.DisableNotification ->
                    NotificationMatchesWorker.cancel(applicationContext, action.match)

                is MainUiAction.EnableNotification ->
                    NotificationMatchesWorker.start(applicationContext, action.match)

                is MainUiAction.MatchesNotFound -> TODO()
                MainUiAction.Unexpected -> TODO()
            }
        }
    }
}
