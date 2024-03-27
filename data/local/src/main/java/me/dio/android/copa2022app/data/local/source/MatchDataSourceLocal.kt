package me.dio.android.copa2022app.data.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.dio.android.copa2022app.data.data.source.MatchesDataSource
import javax.inject.Inject

class MatchDataSourceLocal @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : MatchesDataSource.Local {

    private val key = stringSetPreferencesKey("copa2020app_notification_ids")

    override fun getActiveNotificationIds(): Flow<Set<String>> =
        dataStore.data.map { preferences ->
            preferences[key] ?: setOf()
        }

    override suspend fun enableNotificationFor(id: String) {
        dataStore.edit { mutablePreferences ->
            val currentIds = mutablePreferences[key] ?: setOf()
            mutablePreferences[key] = currentIds + id
        }
    }

    override suspend fun disableNotificationFor(id: String) {
        dataStore.edit { mutablePreferences ->
            val currentIds = mutablePreferences[key] ?: setOf()
            mutablePreferences[key] = currentIds - id
        }
    }
}
