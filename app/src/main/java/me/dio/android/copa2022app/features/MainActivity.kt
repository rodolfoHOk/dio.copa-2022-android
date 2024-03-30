package me.dio.android.copa2022app.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.dio.android.copa2022app.notification_scheduler.NotificationMatchesWorker
import me.dio.android.copa2022app.ui.theme.Copa2022AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Copa2022AppTheme {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { contentPadding ->
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val lifecycle = remember(lifecycleOwner) { lifecycleOwner.lifecycle }

                    LaunchedEffect(lifecycle) {
                        lifecycleScope.launch {
                            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.action.collect { action ->
                                    when (action) {
                                        is MainUiAction.DisableNotification ->
                                            NotificationMatchesWorker.cancel(
                                                applicationContext,
                                                action.match
                                            )

                                        is MainUiAction.EnableNotification ->
                                            NotificationMatchesWorker.start(
                                                applicationContext,
                                                action.match
                                            )

                                        is MainUiAction.MatchesNotFound -> scope.launch {
                                            snackbarHostState.showSnackbar(action.message)
                                        }

                                        is MainUiAction.Unexpected -> scope.launch {
                                            snackbarHostState.showSnackbar(action.message)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    val state by viewModel.state.collectAsState()

                    MainScreen(
                        matches = state.matches,
                        onNotificationClick = viewModel::toggleNotification,
                        paddingValues = contentPadding
                    )
                }
            }
        }
    }
}
